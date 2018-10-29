package com.total.Security.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.total.Security.exception.AlreadyExistException;
import com.total.Security.exception.InvalidTokenException;
import com.total.Security.exception.NotFoundException;
import com.total.Security.exception.RoleNotFoundException;
import com.total.Security.exception.TokenHasExpiredException;
import com.total.Security.exception.UnauthorizedException;
import com.total.Security.model.Login;
import com.total.Security.model.TokenGenerator;
import com.total.Security.model.User;
import com.total.Security.model.Verification;
import com.total.Security.repository.LoginRespository;
import com.total.Security.repository.LoginTokenRepository;
import com.total.Security.repository.UserRepository;
import com.total.Security.repository.VerificationRepository;
import com.total.Security.request.UserCreationRequest;

import com.total.Security.response.UserResponse;
import com.total.Security.utils.UserRole;
import com.total.Security.utils.VerificationStatus;
import com.total.Security.utils.CONSTANTS;
import com.total.Security.utils.DateUtil;
import com.total.Security.utils.EmailUtility;

import com.total.Security.utils.Status;

@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private static final long ONE_MINUTE_IN_MILLIS=60000;

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private LoginTokenRepository logintokenrepo;

	@Autowired
	private LoginRespository loginrepo;

	@Autowired
	private VerificationRepository verificationrepository;

	/*
	 * private String token;
	 * 
	 * public String getToken() { return token; }
	 * 
	 * public void setToken(String token) { this.token = token; }
	 */

	@Transactional
	public void registeruser(UserCreationRequest ur) {
		User u = userrepo.findByEmailAndStatusNot(ur.getEmail(), Status.DELETED);
		if (u != null) {
			throw new NotFoundException("user with email " + ur.getEmail() + " already exists. Try Different Email");
		}
		Login logins = loginrepo.findByUsername(ur.getUsername());
		if (logins != null) {
			throw new AlreadyExistException("Username already exists.");
		}
		if (ur.getUserRole() == null) {
			throw new RoleNotFoundException(
					"Role not found for " + " " + ur.getUsername() + " " + "or" + " " + "Invalid Role");
		}
		User user = new User();
		user.setName(ur.getName());
		user.setEmail(ur.getEmail());
		user.setUserRole(ur.getUserRole());
		user.setStatus(Status.ACTIVE);

		Login login = new Login();
		login.setUsername(ur.getUsername());
		login.setPassword(bcryptEncoder.encode(ur.getPassword()));
		userrepo.save(user);
		User login_user = userrepo.findByEmailAndStatusNot(ur.getEmail(), Status.DELETED);
		login.setUser(login_user);
		loginrepo.save(login);

	}

	@Transactional
	public List<UserResponse> getallusers() {
		List<User> user = userrepo.findAll();
		if (user.isEmpty()) {
			throw new ServiceException("Empty");
		}
		List<UserResponse> UrList = new ArrayList<>();
		user.stream().forEach(i -> {
			UserResponse ur = new UserResponse();
			ur.setId(i.getId());
			ur.setName(i.getName());
			ur.setEmail(i.getEmail());
			UrList.add(ur);
		});
		return UrList;
	}

	@Transactional
	public Map<Object, Object> ResetPassword(String email) {
		LOG.debug("Request to reset password");
		User user = userrepo.findByEmailAndStatusNot(email, Status.DELETED);

		if (user == null) {
			throw new NotFoundException("Email was not found on database");
		}

		Optional<Login> login = loginrepo.findById(user.getLogin().getLogin_id());
		TokenGenerator TG = new TokenGenerator();
		String token = TG.generateToken(login.get().getUsername());

		/*
		 * final long timeInterval = 10000; // generates new token every 10 minutes
		 * Runnable runnable = new Runnable() { public void run() { while (true) { //
		 * ------- code for task to run System.out.println("Hello !!"); // ------- ends
		 * here try { Thread.sleep(timeInterval); } catch (InterruptedException e) {
		 * e.printStackTrace(); } } } }; Thread thread = new Thread(runnable);
		 * thread.start();
		 */
		Verification verification = verificationrepository.findByEmailAndStatusNot(email, VerificationStatus.EXPIRE);
		if (verification != null) {
			verification.setCreatedDate(new Date());
			verification.setToken(token);
			// verification.setToken(getToken());
			verification.setStatus(VerificationStatus.ACTIVE);
			verificationrepository.save(verification);
		} else {
			Verification verifyit = new Verification();
			verifyit.setVerification_id(user.getId());
			verifyit.setEmail(user.getEmail());
			verifyit.setCreatedDate(new Date());
			Calendar date = Calendar.getInstance();
			long t= date.getTimeInMillis();
			Date TenMinutesTokenExpiryTime=new Date(t + (10 * ONE_MINUTE_IN_MILLIS));
			//verifyit.setExpiryDate(DateUtil.getTokenExpireDate(new Date()));
			verifyit.setExpiryDate(TenMinutesTokenExpiryTime);
			verifyit.setToken(token);
			// verifyit.setToken(getToken());
			verifyit.setStatus(VerificationStatus.ACTIVE);
			verificationrepository.save(verifyit);

		}
		EmailUtility.sendResetLink(user.getEmail(), token);
		LOG.debug("Request to reset Password Accepted");
		Map<Object, Object> response = new HashMap<>();
		response.put("Token :", token);
		return response;
	}

	@Transactional
	public void deleteUser(Long userId, Long id) {
		User user = ifuserExists(id);
		isUserAuthorized(userId);
		if (user.getId() == userId)
			throw new UnauthorizedException("You can't Delete this user");
		user.setStatus(Status.DELETED);
		userrepo.save(user);

	}

	private User ifuserExists(Long id) {
		User user = userrepo.findByIdAndStatusNot(id, Status.DELETED);
		if (user == null)
			throw new NotFoundException("Not user was found");
		return user;
	}

	private void isUserAuthorized(Long id) {
		User user = userrepo.findByIdAndStatusNot(id, Status.DELETED);
		if (user == null)
			throw new NotFoundException("Sorry, User Not Found");
		if (!user.getUserRole().equals(UserRole.ADMIN))
			throw new UnauthorizedException("Sorry are are not Admin so You can't perform this Action");
	}

	@Transactional
	public void ChangePassword(Long id, String email, String password, String emailed_token) {
		User user = userrepo.findByIdAndEmail(id, email);
		if (user == null) {
			throw new NotFoundException("Id or Email not found");
		}
		Optional<Login> login = loginrepo.findById(user.getId());
		if (login == null) {
			throw new NotFoundException("Login user not found");
		}
		Verification verification = verificationrepository.findByVerificationid(login.get().getLogin_id());
				
		
		if(verification == null)
		{
			throw new NotFoundException("Verification Impossible, because required Data was not Found");
		}
		// final String tokenexpiryvalidationdate =
		// dateFormat.format(DateUtil.getTokenExpireDate(verification.get().getCreatedDate()));
		String tokenFromVerification = verification.getToken();

		if (tokenFromVerification.equals(emailed_token) == false) {
			throw new InvalidTokenException(CONSTANTS.INVALID_TOKEN);
		}
		if (verification.getExpiryDate().before(new Date())) {
			throw new TokenHasExpiredException("Token has Expired. Try requesting new one !");
		} else {

			login.get().setPassword(bcryptEncoder.encode(password));
			loginrepo.save(login.get());
			System.out.println(verification.getVerificationid());
			verificationrepository.delete(verification);
			
			System.out.println("Verification Entry Deleted");
		}

	}

	/*
	 * public void generate_token_in_interval() { Timer timer = new Timer(); long
	 * delay = 0; long intevalPeriod = 10 * 1000; timer.schedule(task, delay,
	 * intevalPeriod);
	 * 
	 * }
	 */

	/*
	 * if(verification.get().getExpiryDate().before(new Date())) { throw new
	 * TokenHasExpiredException("Tokn has expired"); }
	 */
	/*
	 * public String generate_tokens() { final long timeInterval = 10000; //
	 * generates new token every 10 minutes Runnable runnable = new Runnable() {
	 * public void run() { while (true) { // ------- code for task to run
	 * System.out.println("Hello !!"); // ------- ends here try {
	 * Thread.sleep(timeInterval); } catch (InterruptedException e) {
	 * e.printStackTrace(); } } } }; Thread thread = new Thread(runnable);
	 * thread.start(); return null; }
	 */
}

// schedules the task to be run in an interval

/*
 * private User isExists(Long id) { User user = userrepo.findById(id); if (user
 * == null) throw new NotFoundException("No user found"); return user; }
 * 
 * private void isAuthorized(Long id) { User user = userrepo.findById(id); if
 * (user == null) throw new NotFoundException("Sorry you are not found"); if
 * (!user.getUserRole().equals(UserRole.ADMIN) ||
 * !user.getUserRole().equals(UserRole.SUPERADMIN)) throw new
 * UnauthorizedException("Sorry you are not authorized"); }
 */

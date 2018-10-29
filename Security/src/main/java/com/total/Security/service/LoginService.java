package com.total.Security.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.total.Security.model.LoginToken;
import com.total.Security.model.User;
import com.total.Security.dto.LoginRequestDto;
import com.total.Security.exception.LoginFailException;
import com.total.Security.exception.NotFoundException;
import com.total.Security.exception.UserIdAndTokenMismatchException;
import com.total.Security.model.Login;
import com.total.Security.repository.LoginRespository;
import com.total.Security.repository.LoginTokenRepository;
import com.total.Security.repository.UserRepository;
import com.total.Security.repository.VerificationRepository;
//import com.total.Security.security.TokenProvider;
import com.total.Security.utils.CONSTANTS;
import com.total.Security.utils.LoginStatus;
import com.total.Security.utils.RandomUtils;
import com.total.Security.utils.Status;

@Service
public class LoginService {

	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	LoginRespository loginrepo;

	@Autowired
	UserRepository userrepo;

	@Autowired
	private LoginTokenRepository loginTokenrepo;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	VerificationRepository verifyrepo;

	@Transactional
	public Map<Object, Object> nowLogin(LoginRequestDto loginreqDto) {
		Login login = loginrepo.findByUsername(loginreqDto.getUsername());
		if (login == null) {
			throw new NotFoundException("username with " + " " + loginreqDto.getUsername() + " " + "Not Found");
		}
		if (bcryptEncoder.matches(loginreqDto.getPassword(), login.getPassword())) {
			login.setLoginStatus(LoginStatus.LOGGEDIN);
			login.setLastLogin(new Date());
			login = loginrepo.save(login);
			LoginToken logintoken = loginTokenrepo.findByLoginId(login.getLogin_id());
			if (logintoken == null) {
				logintoken = new LoginToken();
				logintoken.setLoginId(login.getLogin_id());
			}
			logintoken.setToken(RandomUtils.randomString(50));
			Calendar date = Calendar.getInstance();
			logintoken.setTokenExpirationDateTime(new Date(date.getTimeInMillis() + 10 * 60000)); // Expiry time 10
																									// Minutes.
			logintoken.setStatus(Status.ACTIVE);
			User user = userrepo.findByIdAndStatusNot(login.getUser().getId(), Status.DELETED);
			logintoken = loginTokenrepo.save(logintoken);
			Map<Object, Object> response = new HashMap<>();
			response.put("id", login.getLogin_id());
			response.put("user_id", user.getId());
			response.put("User_role", user.getUserRole());
			response.put("token", logintoken.getToken());
			return response;
		}
		throw new LoginFailException(CONSTANTS.EMAIL_AND_PASSWORD_MISMATCH);

	}

	@Transactional
	public void loggedout(Long id) {
		if (id != null) {
			LOG.debug("Logging Out");
			LoginToken logintoken = loginTokenrepo.findByLoginId(id);
			if (logintoken == null) {
				throw new UserIdAndTokenMismatchException(CONSTANTS.USER_ID_NOT_FOUND);
			}
			logintoken.setStatus(Status.BLOCKED);
			logintoken.setToken(null);
			Optional<Login> login = loginrepo.findById(id);
			login.get().setLoginStatus(LoginStatus.LOGGEDOUT);
			loginrepo.save(login.get());
			logintoken.setTokenExpirationDateTime(new Date());
			loginTokenrepo.save(logintoken);
		}
	}

}

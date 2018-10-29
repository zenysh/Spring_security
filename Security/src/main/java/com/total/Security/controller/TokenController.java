package com.total.Security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.total.Security.exception.LoginFailException;
import com.total.Security.exception.NotFoundException;
import com.total.Security.model.AuthToken;
import com.total.Security.model.Login;
import com.total.Security.repository.LoginRespository;
import com.total.Security.request.LoginRequest;
import com.total.Security.security.JwtTokenProvider;
//import com.total.Security.security.TokenProvider;
import com.total.Security.service.UserService;
// import com.total.Security.service.UserService;
import com.total.Security.utils.CONSTANTS;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

//	@Autowired
//	private TokenProvider provider;
	
	@Autowired
	private JwtTokenProvider provider;

	@Autowired
	LoginRespository loginrepo;

	@Autowired
	private UserService userService;
	
	public String tokens;

	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody LoginRequest loginrequest) throws AuthenticationException {
		Login login = loginrepo.findByUsername(loginrequest.getUsername());
		if (login == null) {
			throw new NotFoundException("User with the username " + loginrequest.getUsername() + " not found!");
		}
		String login_detail = login.getPassword();
		String loginrequest_detail = loginrequest.getPassword();
		if (bcryptEncoder.matches(loginrequest_detail, login_detail)) {
			final Login logins = loginrepo.findByUsername(loginrequest.getUsername());
			tokens = provider.GenerateToken(logins);
			//tokens = provider.generateToken(logins);
		} else {
			tokens = "Username and password is incorrect";
			throw new LoginFailException(CONSTANTS.USERNAME_AND_PASSWORD_MISMATCH);
		}
		return ResponseEntity.ok(new AuthToken(tokens));

	}
}

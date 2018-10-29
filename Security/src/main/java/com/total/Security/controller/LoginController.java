package com.total.Security.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.total.Security.dto.LoginRequestDto;
//import com.total.Security.security.TokenProvider;
import com.total.Security.service.LoginService;

@RestController
@RequestMapping("rest")
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginservice;
	
//	@Autowired
//	TokenProvider tokenprovide;
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Object> letslogin(@RequestBody LoginRequestDto loginreqdto,
			@RequestHeader String authorization) {
		Map<Object, Object> response = loginservice.nowLogin(loginreqdto);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<Object> letsLogout(@RequestHeader Long User_id) {
		loginservice.loggedout(User_id);
		return new ResponseEntity<Object>("Loggedout Successful", HttpStatus.OK);

	}

}

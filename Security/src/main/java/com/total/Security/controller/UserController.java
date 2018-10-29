package com.total.Security.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.total.Security.request.LoginRequest;
import com.total.Security.request.UserCreationRequest;
import com.total.Security.request.UserRequest;
import com.total.Security.response.UserResponse;
import com.total.Security.service.UserService;

import io.swagger.annotations.ApiOperation;
import com.total.Security.model.Login;
import com.total.Security.repository.LoginRespository;
import com.total.Security.repository.UserRepository;

@RestController
@RequestMapping("rest")
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private LoginRespository loginrepo;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository userrepo;
	
	String good;
	
	@RequestMapping(value = "register_user",method=RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody UserCreationRequest requests){
		service.registeruser(requests);
		return new ResponseEntity<>("Successfully Created",HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/validate",method=RequestMethod.POST)
	public String validate(@RequestBody LoginRequest loginrequest) {
	Login login = loginrepo.findByUsername(loginrequest.getUsername());
	if(bcryptEncoder.matches(loginrequest.getPassword(), login.getPassword())) {
	good = "matched";
	}else
	{
     good = "mismatched";
	}
	
	return good;
    }
	
	@ApiOperation(value = "forget password", notes = "Api to forget password")
	@RequestMapping(value = "/forgetPassword/{email:.+}", method = RequestMethod.POST)
	public ResponseEntity<Object> forgetPassword(@PathVariable String email) {
		LOG.debug("Request Accepted for Reset password");
		Map<Object, Object> response = service.ResetPassword(email);
		return new ResponseEntity<Object>("Check your email to reset your password. Token expires every 10 minutes " + response, HttpStatus.OK);
	}
	@RequestMapping(value="/getUsers", method = RequestMethod.GET)
	public ResponseEntity<Object> getallUsers()
	{
	List<UserResponse> rr = service.getallusers();
	return new ResponseEntity<>(rr,HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/{id}", method= RequestMethod.DELETE)
	public ResponseEntity<Object>deleteUser(@RequestHeader Long userId, @PathVariable Long id, @RequestHeader String authorization)
	{
		service.deleteUser(userId, id);
		return new ResponseEntity<Object>("Deletion Successful", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/{email:.+}", method = RequestMethod.PUT)
	public ResponseEntity<Object> ChangePassword(@PathVariable Long id ,@PathVariable String email, @RequestParam String password, @RequestParam String emailed_token)
	{
		service.ChangePassword(id, email, password, emailed_token );
		return new ResponseEntity<Object>("Password changed Successfully" , HttpStatus.OK);
	}
	
}

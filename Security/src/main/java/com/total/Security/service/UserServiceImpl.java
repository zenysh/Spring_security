package com.total.Security.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.total.Security.dao.UserDao;
import com.total.Security.model.Login;
import com.total.Security.model.UserServices;

@Service(value="userServices")
public class UserServiceImpl implements UserDetailsService,UserServices{

	@Autowired
	private UserDao userDao;
	@Override
	public Login findOne(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String userId ) throws UsernameNotFoundException {
		Login user = userDao.findByUsername(userId);
		if(user == null)
		{
			throw new UsernameNotFoundException("Invalid username");
		}
		return new User(user.getUsername(),user.getPassword(),getAuthority());
		
	}
	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}


}

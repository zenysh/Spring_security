package com.total.Security.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.total.Security.model.Login;

@Repository
public interface UserDao extends CrudRepository<Login,Long> {
	
	Login findByUsername(String username);
	

}

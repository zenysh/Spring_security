package com.total.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.total.Security.model.LoginToken;
import com.total.Security.utils.Status;

@Repository
public interface LoginTokenRepository extends JpaRepository<LoginToken, Long> {
	
	LoginToken findByLoginIdAndToken(Long userId, String token);


	LoginToken findByLoginId(Long id);


	LoginToken findByLoginIdAndStatusNot(Long id, Status deleted);

}

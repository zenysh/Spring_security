package com.total.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.total.Security.model.User;
import com.total.Security.utils.Status;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailAndStatusNot(String email, Status deleted);

	User findByName(String username);

	User findByIdAndStatusNot(Long id, Status deleted);
	
	User findByIdAndEmail(Long id, String email);

}

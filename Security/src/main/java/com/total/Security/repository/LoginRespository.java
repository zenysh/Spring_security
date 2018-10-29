package com.total.Security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.total.Security.model.Login;

@Repository
public interface LoginRespository extends JpaRepository<Login,Long>{
	
   
	Login findByUsername(String username);
    Optional<Login> findById(Long id);
	

}

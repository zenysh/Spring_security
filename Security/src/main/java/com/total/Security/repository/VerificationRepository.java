package com.total.Security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.total.Security.model.Verification;
import com.total.Security.utils.VerificationStatus;

public interface VerificationRepository extends JpaRepository <Verification, Long>{
	
	Verification findByEmailAndStatusNot(String email, VerificationStatus expire);

	Verification findByTokenAndStatusNot(String token, VerificationStatus expire);
	
	//void deleteById(Optional<Verification> verification);
	void delete(Verification verification);
	
	
	Verification findByVerificationid(Long id);

}

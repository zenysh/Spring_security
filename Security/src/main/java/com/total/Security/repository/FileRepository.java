package com.total.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.total.Security.model.Filess;

@Repository
public interface FileRepository extends JpaRepository<Filess,Long> {
	
	Filess findByFileid(Long id);
	
	

}

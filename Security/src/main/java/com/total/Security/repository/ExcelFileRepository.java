package com.total.Security.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.total.Security.model.ExceltestFile;

@Repository
public interface ExcelFileRepository extends JpaRepository<ExceltestFile, Long> {
	
	List<ExceltestFile> findByUserNames(String search,Pageable pageable);
	
	

}

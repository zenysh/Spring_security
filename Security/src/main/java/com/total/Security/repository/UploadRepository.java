package com.total.Security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.total.Security.model.Upload;

@Repository
public interface UploadRepository  extends JpaRepository<Upload, Long>{
	
}

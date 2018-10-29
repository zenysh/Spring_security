package com.total.Security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import com.total.Security.exception.InvalidJwtAuthenticationException;
import com.total.Security.exception.InvalidTokenException;
import com.total.Security.exception.NotFoundException;
import com.total.Security.model.Filess;
import com.total.Security.property.FileStorageProperties;
import com.total.Security.repository.FileRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

	@Autowired
	FileRepository filerepo;

	private final Path fileStorageLocation;

	@Autowired
	public FileService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new InvalidTokenException("Could not create the directory where the uploaded files will be stored.");
		}
	}

	@Transactional
	public String upload_file(MultipartFile multipartFile) {
		if (multipartFile == null) {
			throw new NotFoundException("File path or file itself is Empty");
		}

		String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		try {
			// check if file's name contains invalid Characters...
			if (filename.contains("..")) {
				throw new InvalidJwtAuthenticationException("Invalid Path Sequence" + filename);
			}

			Filess files = new Filess(filename, multipartFile.getContentType(), multipartFile.getBytes());
			filerepo.save(files);
		} catch (IOException ex) {
			throw new InvalidJwtAuthenticationException(
					"Something is wrong with" + filename + " " + "Couldn't Store the File");
		}
		return filename;
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new NotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new NotFoundException("File not found " + fileName);
		}
	}
}

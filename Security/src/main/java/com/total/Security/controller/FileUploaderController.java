package com.total.Security.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class FileUploaderController {

	public static String uploadDirectory = System.getProperty("user.dir") + "/Uploaded_files";

	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public ResponseEntity<Object> Upload_files(@RequestParam("UploadFiles") MultipartFile file) throws IOException {
		File convertFile = new File("C:\\Users\\Jenish\\Downloads\\Compressed\\Security\\Security\\Uploaded_files\\"
				+ file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream stream = new FileOutputStream(convertFile);
		stream.write(file.getBytes());
		stream.close();
		return new ResponseEntity<>("File uploaded Successfully", HttpStatus.OK);

	}

}

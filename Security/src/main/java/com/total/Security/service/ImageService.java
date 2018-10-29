package com.total.Security.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.total.Security.model.Upload;
import com.total.Security.repository.UploadRepository;
import com.total.Security.utils.Base64Util;
import com.total.Security.utils.FileUtil;

@Service
public class ImageService {
	@Autowired
	UploadRepository uploadrepo;

	public String getImage(String image) {
		File file = null;
		file = new File(image);
		String returnFile = null;
		try {
			if (file != null)
				returnFile = Base64Util.encodeFileToBase64Binary(file);
		} catch (IOException e) {
			if (file != null)
				file.delete();
			e.printStackTrace();
		}
		return returnFile;
	}

	@Transactional
	public File setpicture(String picture) {
		File file = null;
		try {
			file = FileUtil.write(String.valueOf(new Date().getTime()).concat(".").concat("png"), picture);
			System.out.println("image:" + file);
		} catch (Exception e) {
			e.printStackTrace();
			if (file != null) {
				file.delete();
			}
		}
		return file;
	}

	public void setImages(String image) {
		Upload upload = new Upload();
		File file = null;
		try {
			if (image != null) {
				file = setpicture(image);
				if (file != null)
					upload.setImage_data(file.getAbsolutePath());
				uploadrepo.save(upload);
			}
		} catch (Exception e) {
			if (file != null)
				file.delete();
			e.printStackTrace();
		}
	}
}

package com.total.Security.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@SuppressWarnings("serial")
public class Filess implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileid;

	private String fileName;
	
	
	private String fileType;
	
	@Lob
	private byte[]  data;
	
	public Filess()
	{
		
	}
	
	public Filess(String fileName, String fileType, byte[] data)
	{
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
		
	}

	public Long getFileid() {
		return fileid;
	}

	public void setFile_id(Long fileid) {
		this.fileid = fileid;
	}

	public byte[]  getData() {
		return data;
	}

	public void setData(byte[]  data) {
		this.data = data;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}

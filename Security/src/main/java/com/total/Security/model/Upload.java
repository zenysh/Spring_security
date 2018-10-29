package com.total.Security.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
@SuppressWarnings("serial")
public class Upload implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long pictureid;
	
	private String image_data;

	public long getPictureid() {
		return pictureid;
	}

	public void setPictureid(long pictureid) {
		this.pictureid = pictureid;
	}

	public String getImage_data() {
		return image_data;
	}

	public void setImage_data(String image_data) {
		this.image_data = image_data;
	}

}

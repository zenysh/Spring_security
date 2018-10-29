package com.total.Security.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.total.Security.utils.Status;

@SuppressWarnings("serial")
@Entity
public class LoginToken implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long loginId;
	private String token;
	@Enumerated(EnumType.STRING)
	private Status status;
	private Date tokenExpirationDateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getTokenExpirationDateTime() {
		return tokenExpirationDateTime;
	}

	public void setTokenExpirationDateTime(Date tokenExpirationDateTime) {
		this.tokenExpirationDateTime = tokenExpirationDateTime;
	}

}

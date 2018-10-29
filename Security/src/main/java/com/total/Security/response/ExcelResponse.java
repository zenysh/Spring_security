package com.total.Security.response;

import java.io.Serializable;

public class ExcelResponse implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7312607192662049240L;

	private String Listid;

	private String userNames;

	private String address;

	private String phonenumber;

	public String getListid() {
		return Listid;
	}

	public void setListid(String listid) {
		Listid = listid;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

}

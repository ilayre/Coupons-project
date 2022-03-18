package com.ilay.coupons.beans;

import javax.xml.bind.annotation.XmlRootElement;

import com.ilay.coupons.enums.UserType;

@XmlRootElement
public class User {

	private long id;
	private String name;
	private String email;
	private String password;
	private UserType userType;
	
	public User() {
	}
	
	public User(long id, String name, String password, UserType userTyper) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.userType = userTyper;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", userType="
				+ userType + "]";
	}

	

}
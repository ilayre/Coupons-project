package com.ilay.coupons.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlEnum
@XmlRootElement
public enum UserType {
	
	COMPANY("COMPANY"),
	CUSTOMER("CUSTOMER"),
	ADMIN("ADMIN");
	
	private String userType;
	
	private UserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public static UserType fromString(final String s) {
		return UserType.valueOf(s);
	}

}

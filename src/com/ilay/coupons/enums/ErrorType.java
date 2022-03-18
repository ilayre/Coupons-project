package com.ilay.coupons.enums;

public enum ErrorType {
	
	SYSTEM_MESSAGE(501),
	GENERAL_ERROR(601),
	INVALID_PARAMETER(603),
	INVALID_LOGIN(604),
	SYSTEM_ERROR(605),
	SERVER_RESTART(606),
	UNAUTHORIZED_ACTION(607),
	NAME_IS_ALREADY_EXIST(608),
	EMAIL_IS_ALREADY_EXIST(609),
	ID_IS_ALREADY_EXIST(759),
	LOGIN_FAIL(953),
	SESSION_TIMEOUT(609);
	
	private int number;
	
	private ErrorType(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
	
	public static ErrorType fromString(final String s) {
		return ErrorType.valueOf(s);
	}

}

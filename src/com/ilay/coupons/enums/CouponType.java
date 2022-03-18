package com.ilay.coupons.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;


@XmlEnum
@XmlRootElement
public enum CouponType {
	
	FOOD("FOOD"),
	VECATION("VECATION"),
	ELECTRIC("ELECTRIC"),
	FUN("FUN");
	
	private String name;
	
	CouponType(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	 
		
	
	
}
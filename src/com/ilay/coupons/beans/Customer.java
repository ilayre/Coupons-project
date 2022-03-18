package com.ilay.coupons.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
	
	private long customerId;
	private String customerName;
	private String customerPassword;
	private String customerEmail;
	private List<Coupon> customerCouponsList = new ArrayList<>();
	
	public Customer() {
		super();
	}
	
	public Customer(String customerName, String customerPassword, String customerEmail) {
		super();
		this.customerName = customerName;
		this.customerPassword = customerPassword;
		this.customerEmail = customerEmail;
	}
	
	public Customer(long customerId, String customerName, String customerPassword, String customerEmail) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPassword = customerPassword;
		this.customerEmail = customerEmail;
	}


	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public List<Coupon> getCustomerCouponsList() {
		return customerCouponsList;
	}

	public void setCustomerCouponsList(List<Coupon> customerCouponsList) {
		this.customerCouponsList = customerCouponsList;
	}
	

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerPassword="
				+ customerPassword + ", customerEmail=" + customerEmail + ", customerCouponsList=" + customerCouponsList
				+ "]";
	}

	

	
}
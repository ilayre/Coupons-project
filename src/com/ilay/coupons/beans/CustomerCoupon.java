package com.ilay.coupons.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerCoupon {
	
	private long customerId;
	private long couponId;
	
	public  CustomerCoupon() {
	}
	
	public CustomerCoupon(long customerId, long couponId) {
		super();
		this.customerId = customerId;
		this.couponId = couponId;
	}

	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	@Override
	public String toString() {
		return "CusatomerCoupon [customerId=" + customerId + ", couponId=" + couponId + "]";
	}
	
	
	
	

}

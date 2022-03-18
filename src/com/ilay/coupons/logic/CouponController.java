package com.ilay.coupons.logic;

import java.util.List;

import com.ilay.coupons.beans.Coupon;
import com.ilay.coupons.beans.CustomerCoupon;
import com.ilay.coupons.dao.CouponDao;
import com.ilay.coupons.enums.CouponType;
import com.ilay.coupons.enums.ErrorType;
import com.ilay.coupons.exception.ApplicationException;


public class CouponController {

	private CouponDao couponDao;
	
	public CouponController() {
		super();
		this.couponDao = new CouponDao();
	}
	
	public void createCoupon (Coupon coupon) throws ApplicationException {
		validateCreateCoupon(coupon);	//checks for coupon validation.
		this.couponDao.createCoupon(coupon);
	}
	
	public Coupon getCouponByCouponId(long couponId) throws ApplicationException {
		if(couponDao.getCouponByCouponId(couponId)==null) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Coupon ID not exist");
		}
		return couponDao.getCouponByCouponId(couponId);
	}
	
	public void deleteCoupon(long couponId) throws ApplicationException {
		if(couponDao.getCouponByCouponId(couponId)==null) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Coupon ID not exist");
		}
		couponDao.deleteCouponFromFkTable(couponId);
		couponDao.deleteCoupon(couponId);
	}
	
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		if(couponDao.getCouponByCouponId(coupon.getCouponId())==null) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Coupon ID not exist");
		}
		couponDao.updateCoupon(coupon);
	}
	
	public List<Coupon> getCouponList() throws ApplicationException {
		if(couponDao.getCouponList()==null) {
			throw new ApplicationException(ErrorType.SYSTEM_MESSAGE,"There are no coupons available, List is empty");
		}
		return couponDao.getCouponList();
	}
	
	public boolean checkCouponDate (long couponId) throws ApplicationException {
		if(couponDao.getCouponByCouponId(couponId)==null) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Coupon ID not exist");
		}
		return couponDao.checkCouponDate(couponId);
	}
	
	public void deleteExpiredCoupon() throws ApplicationException {
		couponDao.deleteExpiredCoupon();
	}
	
	public List<Coupon> getCouponsByMinimumPrice(double price) throws ApplicationException {
		if(couponDao.getCouponsByMinimumPrice(price)==null) {
			throw new ApplicationException(ErrorType.SYSTEM_MESSAGE,"There are no coupons available, List is empty");
		}
		return couponDao.getCouponsByMinimumPrice(price);
	}
	
	public List<Coupon> getCouponsByMaximumPrice(double price) throws ApplicationException {
		if(couponDao.getCouponsByMaximumPrice(price)==null) {
			throw new ApplicationException(ErrorType.SYSTEM_MESSAGE,"There are no coupons available, List is empty");
		}
		return couponDao.getCouponsByMaximumPrice(price);
	}
		
	public List<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException {
		if(couponDao.getCouponsByType(couponType)==null) {
			throw new ApplicationException(ErrorType.SYSTEM_MESSAGE,"There are no coupons available, List is empty");
		}
		return couponDao.getCouponsByType(couponType);
	}

	public void updateCouponAmount(long couponId, int amount) throws ApplicationException {
		
		if(couponDao.getCouponByCouponId(couponId)==null) {
			throw new ApplicationException(ErrorType.INVALID_PARAMETER, "Coupon ID not exist");
		}
		couponDao.updateCouponAmount(couponId, amount);
	}
	
	public void purchaseCoupon(CustomerCoupon customerCoupon) throws ApplicationException {
		
		if(couponDao.getCouponByCouponId(customerCoupon.getCouponId()).getAmount()==0) {
			throw new ApplicationException(ErrorType.SYSTEM_MESSAGE, "Purchase faild, theres no coupon left");
		}
		couponDao.purchaseCoupon(customerCoupon);
		couponDao.updateCouponAmount(customerCoupon.getCouponId(),(couponDao.getCouponByCouponId(customerCoupon.getCouponId()).getAmount()-1));	//update amount -1;
	}
	
	public void validateCreateCoupon(Coupon coupon) throws ApplicationException {
		if(couponDao.isCouponExistByName(coupon)) { //if true it means the coupon exist in the DB.
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXIST, "Create coupon has failed, coupon is already exist");
		}
	}
	
	
}	

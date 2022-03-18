package com.ilay.coupons.beans;

import java.beans.ConstructorProperties;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.ilay.coupons.beans.Company;
import com.ilay.coupons.dao.CompanyDao;
import com.ilay.coupons.dao.CouponDao;
import com.ilay.coupons.dao.CustomerDao;
import com.ilay.coupons.enums.CouponType;
import com.ilay.coupons.enums.UserType;
import com.ilay.coupons.exception.ApplicationException;
import com.ilay.coupons.logic.CompanyController;
import com.ilay.coupons.logic.CouponController;
import com.ilay.coupons.logic.CustomerController;
import com.ilay.coupons.utils.JdbcUtils;

	public class Test {

		public static void main(String[] args) throws SQLException, InterruptedException, ApplicationException {
			JdbcUtils.getConnection();
			Thread.sleep(3000);

			CompanyController companyController = new CompanyController();
			CouponController couponController = new CouponController();
			CustomerController customerController = new CustomerController();
			CompanyDao companyDao = new CompanyDao();
			CouponDao couponDao = new CouponDao();
			CustomerDao customerDao = new CustomerDao();
			User user = new User();
		//	user.setUserType(UserType.COMPANY);
			
		//	user.getUserType();
			String companyEmail = "company2@gmail.com";
			
			System.out.println(customerController.checkLogin("ilay@gmail.com", "Ii156789"));
			//System.out.println(customerController.checkLogin("ilay", "password"));
			
			
			
		//	couponDao.R();

		//	List<Coupon> allCoupons = new ArrayList<Coupon>();
		
			
		//	allCoupons = couponController.getCouponList();
			
		//	for(Coupon currentCoupon : allCoupons) {
		//		if(!couponController.checkCouponDate(currentCoupon.getCouponId())){
		//			couponController.deleteCoupon(currentCoupon.getCouponId());
		//		}
		//	}
			
			
		//	Coupon coupon2 = new Coupon("coupon2","04.09.2018","04.09.2019",5,CouponType.FUN,"msg",650, "imagelink", 10);
		//	Coupon coupon2 = new Coupon("coupon2","04092018","04092019",5,CouponType.FUN,"msg",650, "imagelink", 3);
		//	Coupon coupon3 = new Coupon("coupon3","04092018","04092019",5,CouponType.FUN,"msg",650, "imagelink", 3);
		//	Coupon coupon4 = new Coupon("coupon4","04092018","04092019",5,CouponType.FUN,"msg",650, "imagelink", 3);
		//	Coupon coupon5 = new Coupon("coupon5","04092018","04092019",5,CouponType.FUN,"msg",650, "imagelink", 3);
		//	couponController.updateCoupon(7, "coupon1","2017-01-01","2018-01-01",5,CouponType.FUN,"msg",650, "imagelink", 10);
		//	couponController.updateCoupon(8, "coupon1","2017-01-01","2019-01-01",5,CouponType.FUN,"msg",650, "imagelink", 10);
			
		//	couponController.createCoupon(coupon2);
		//	couponController.createCoupon(coupon2);
		//	couponController.createCoupon(coupon3);
		//	couponController.createCoupon(coupon4);
		//	couponController.createCoupon(coupon5);
			
		//	 Create new company
		//	 Company company = new Company("UPS","12345","ilayr@ups.co.il");
		//	 companyDao.createCompany(company);

		//	Update company
		//	companyDao.updateCompany(2, "FEDEX", "54321", "ilayr@fedex.co.il");
		//	companyController.updateCompany(10, "new Company", "AAA5214854", "newcompany@gmail.com");
			
			 
		//	 Delete company
		//	companyDao.deleteCompany(3);

			

		//  Get all companies
		//	 System.out.println(companyDao.getCompanyList());

		//	Create coupon
		//	Coupon coupon = new Coupon("hgjb", "2018-10-1", "2018-9-1", 6, CouponType.FUN, "have fun", 650, "imagelink", 8);
		//	couponDao.createCoupon(coupon);
			
		//	 Update coupon
		//	 couponDao.updateCoupon(2, "coupon1", "1.9.19", "1.10.1", 8, CouponType.FUN, "have fun", 500, "imagelink", 3);

		//	 Get all coupons
		//	 System.out.println(couponDao.getCouponList());		
			
		//	 Delete coupon
		//	 couponDao.deleteCoupon(2);
			
		//	 System.out.println("Deleting..");

		// 	Create new customer
		//	Customer customer = new Customer(1, "ilay", "AAaassa5124");
		//	customerController.createCustomer(customer);

		//	 Update customer
		//	 customerDao.updateCustomer(1, "ilay reshef", "123456");

		//	 Get all customers
		//	 System.out.println(customerDao.getCustomerList());
			
		//	 Delete customer
		//	 customerDao.deleteCustomer(1);
		//	 System.out.println("Deleting..");	
			
		//	couponController.updateCouponAmount(8, 0);
			
		//	customerController.purchaseCoupon(2, 8);
		//	customerController.deleteCustomer((long) 2);
			
		//	System.out.println(companyController.getCompanyByCompanyName("JBr"));
		
		}
	}
	

	


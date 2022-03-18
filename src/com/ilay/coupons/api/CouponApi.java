package com.ilay.coupons.api;

import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ilay.coupons.beans.Coupon;
import com.ilay.coupons.beans.CustomerCoupon;
import com.ilay.coupons.enums.CouponType;
import com.ilay.coupons.exception.ApplicationException;
import com.ilay.coupons.logic.CouponController;

@Path("/coupons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CouponApi {
	
	private CouponController couponController;
	
	public CouponApi() {
		super();
		this.couponController = new CouponController();
	}
	
	@POST
	public void createCoupon (Coupon coupon) throws ApplicationException {
		couponController.createCoupon(coupon);
	}
	
	@GET
	@Path("/{couponId}")
	public Coupon getCouponByCouponId(@PathParam("couponId")long couponId) throws ApplicationException {
		 return couponController.getCouponByCouponId(couponId);
	}
	
	@DELETE
	@Path("/delete/{couponId}")
	public void deleteCoupon(@PathParam("couponId")long couponId) throws ApplicationException {
		couponController.deleteCoupon(couponId);
	}
	
	@PUT
	@Path("/update")
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		couponController.updateCoupon(coupon);
	}
	
	@GET
	@Path("/getList")
	public List<Coupon> getCouponList() throws ApplicationException {
		return couponController.getCouponList();
	}
	
	
	@GET
	@Path("/byMinimum/{price}")
	public List<Coupon> getCouponsByMinimumPrice(@QueryParam("price")double price) throws ApplicationException {
		return couponController.getCouponsByMinimumPrice(price);
	}
	
	@GET
	@Path("/byMaximum/{price}")
	public List<Coupon> getCouponsByMaximumPrice(@QueryParam("price")double price) throws ApplicationException {
		return couponController.getCouponsByMaximumPrice(price);
	}
	
	@GET
	@Path("/byType/{couponType}")
	public List<Coupon> getCouponsByType(@QueryParam("couponType")CouponType couponType) throws ApplicationException {
		return couponController.getCouponsByType(couponType);
	}
	

	@POST
	@Path("/purchase")
	public void purchaseCoupon(CustomerCoupon customerCoupon) throws ApplicationException {
		couponController.purchaseCoupon(customerCoupon);
	}

}

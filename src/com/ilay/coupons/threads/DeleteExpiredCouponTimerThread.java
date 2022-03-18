package com.ilay.coupons.threads;
import java.util.Date;
import java.util.TimerTask;

import com.ilay.coupons.dao.CouponDao;
import com.ilay.coupons.exception.ApplicationException;


public class DeleteExpiredCouponTimerThread extends TimerTask{
	
	@Override
	public void run() {
		
		
		try {
			System.out.println("Timer task started at: "+new Date());
			CouponDao.deleteExpiredCoupon();
			System.out.println("Timer task finished at: "+new Date());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}


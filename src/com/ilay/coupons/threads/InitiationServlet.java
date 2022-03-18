package com.ilay.coupons.threads;

import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.Timer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.ilay.coupons.dao.CouponDao;
import com.ilay.coupons.exception.ApplicationException;


/**
 * Servlet implementation class InitiationServlet.
 * This servlet is responsible for every method that needed to be start on load of server
 */
@WebServlet("/InitiationServlet")
public class InitiationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws ApplicationException 
     * @see HttpServlet#HttpServlet()
     */
    public InitiationServlet() throws ApplicationException {
    	startDeleteExpiredCoupons();
    	
    }

    /**
     * start Delete Expired Coupons.
     * @throws ApplicationException 
     */
	private void startDeleteExpiredCoupons() throws ApplicationException {
		
		// Getting the time of startup of the server and set it for next day at midnight.
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.HOUR, 00);
		gc.set(Calendar.MINUTE, 00);
		gc.set(Calendar.SECOND, 00);
		gc.add(Calendar.DAY_OF_MONTH, 1);
		
		// Setting the start and time of running of the thread.
		Timer timer = new Timer();
		timer.schedule(null, gc.getTime(), 1000 * 60 * 60 * 24);
		CouponDao.deleteExpiredCoupon();
		
	}
    
    
	
	

}

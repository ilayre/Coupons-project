package com.ilay.coupons.threads;

import java.util.Date;


public class TimerTaskThread {

	
	public void run() {
		System.out.println(new Date());
		System.out.println("thread starts");
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("thread ends");
		
	}

}

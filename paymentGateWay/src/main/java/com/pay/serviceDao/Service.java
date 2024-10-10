package com.pay.serviceDao;

import com.razorpay.RazorpayException;

public interface Service {

	public String createOrder(int amount) throws RazorpayException;	
}

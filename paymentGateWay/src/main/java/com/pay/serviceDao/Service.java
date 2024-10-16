package com.pay.serviceDao;

import com.pay.entities.OrderEntities;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

public interface Service {

	public OrderEntities createOrder(int amount) throws RazorpayException;	
}

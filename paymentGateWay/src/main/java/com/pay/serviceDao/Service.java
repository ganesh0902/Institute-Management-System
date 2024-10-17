package com.pay.serviceDao;

import com.pay.entities.OrderEntities;
import com.pay.entities.PaymentRecord;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

public interface Service {

	public OrderEntities createOrder(int amount,int stdId) throws RazorpayException;
	public PaymentRecord savePaymentRecord(PaymentRecord payment);
}

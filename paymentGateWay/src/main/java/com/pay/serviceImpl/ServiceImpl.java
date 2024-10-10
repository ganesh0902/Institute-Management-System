package com.pay.serviceImpl;

import org.json.JSONObject;

import com.pay.serviceDao.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

public class ServiceImpl implements Service{

	@Override
	public String createOrder(int amount) throws RazorpayException {

		RazorpayClient razorpayClient = new RazorpayClient("", "");
		
		RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");

		JSONObject orderRequest  = new JSONObject();
		orderRequest.put("amount",50000);
		orderRequest.put("currency","INR");
		orderRequest.put("receipt", "receipt#1");
		JSONObject notes = new JSONObject();
		notes.put("notes_key_1","Tea, Earl Grey, Hot");
		orderRequest.put("notes",notes);

		Order order = razorpayClient.orders.create(orderRequest);
		
		return null;
	}	
}
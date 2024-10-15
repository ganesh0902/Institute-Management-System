package com.pay.serviceImpl;

import org.json.JSONObject;
import java.util.Date;

import com.pay.serviceDao.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

	@Override
	public String createOrder(int amount) throws RazorpayException {

	    // Initialize RazorpayClient with the correct API keys
	    RazorpayClient razorpayClient = new RazorpayClient("rzp_test_u7Jblf9H9semGK", "MVdLDuamaT0QC1vgNTRHFGoq");

	    // Prepare the order request
	    JSONObject orderRequest = new JSONObject();
	    orderRequest.put("amount", amount);  // Use the passed amount
	    orderRequest.put("currency", "INR");
	    orderRequest.put("receipt", "receipt#1");

	    JSONObject notes = new JSONObject();
	    notes.put("notes_key_1", "Tea, Earl Grey, Hot");
	    orderRequest.put("notes", notes);

	    // Create the order
	    Order order = razorpayClient.orders.create(orderRequest);

	    // Extract the values from the order response
	    String objectId = (String) order.get("id");
	    String entities = (String) order.get("entity");
	    int amt = (Integer) order.get("amount");
	    int amount_paid = (Integer) order.get("amount_paid");
	    int amount_due = (Integer) order.get("amount_due");
	    String currency = (String) order.get("currency");
	    String receipt = (String) order.get("receipt");
	    Date created_at = (Date) order.get("created_at");	    	    

	    System.out.println(order);

	    return objectId;
	}
	
}
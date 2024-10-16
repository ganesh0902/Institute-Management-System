package com.pay.serviceImpl;

import org.json.JSONObject;
import java.util.Date;

import com.pay.entities.OrderEntities;
import com.pay.serviceDao.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

	@Override
	public OrderEntities createOrder(int amount) throws RazorpayException {

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

	    System.out.println(order);
	    
	    // Extract the values from the order response
	    
	    OrderEntities orderDto = new OrderEntities();
	    
	    orderDto.setOrderId(order.get("id"));
	    orderDto.setEntity(order.get("entity"));	    
	    orderDto.setAmount(order.get("amount"));
	    orderDto.setAmount_paid(order.get("amount_paid"));
	    orderDto.setAmount_due(order.get("amount_due"));
	    orderDto.setCurrency(order.get("currency"));
	    orderDto.setReceipt(order.get("receipt"));
	    orderDto.setCreated_at(order.get("created_at"));
	    orderDto.setStatus(order.get("status"));
	    	    	    	      
	    System.out.println("dto"+orderDto);

	    return orderDto;
	}
	
}
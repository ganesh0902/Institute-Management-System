package com.pay.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.pay.entities.OrderEntities;
import com.pay.entities.PaymentRecord;
import com.pay.repository.OrderRepository;
import com.pay.repository.PaymentRecordRepossitory;
import com.pay.serviceDao.Service;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private PaymentRecordRepossitory payRepository;
	
	@Override
	public OrderEntities createOrder(int amount,int stdId) throws RazorpayException {

		try
		{
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
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String currentDate = currentDateTime.format(formatter);
	    
	    // Extract the values from the order response
	    
	    OrderEntities orderDto = new OrderEntities();
	    
	    orderDto.setId(order.get("id"));
	    orderDto.setEntity(order.get("entity"));	    
	    orderDto.setAmount(order.get("amount"));
	    orderDto.setAmount_paid(order.get("amount_paid"));
	    orderDto.setAmount_due(order.get("amount_due"));
	    orderDto.setCurrency(order.get("currency"));
	    orderDto.setReceipt(order.get("receipt"));
	    orderDto.setCreated_at(order.get("created_at"));
	    orderDto.setStatus(order.get("status"));
	    orderDto.setStdId(stdId);
	    
	    
	    	    	    	      
	    System.out.println("order created and save" +orderDto);

	    repo.save(orderDto);
	    
	    return orderDto;
		} catch (RazorpayException e) {
	        System.err.println("Error while creating order in Razorpay: " + e.getMessage());
	        throw new RuntimeException("Razorpay order creation failed", e); // Wrap and rethrow the exception

	    } catch (JSONException e) {
	        System.err.println("Error while parsing JSON response: " + e.getMessage());
	        throw new RuntimeException("Error parsing Razorpay order response", e);

	    } catch (Exception e) {
	        System.err.println("General error occurred: " + e.getMessage());
	        throw new RuntimeException("Error occurred while creating order", e);
	    }
	}

	@Override
	public PaymentRecord savePaymentRecord(PaymentRecord payment) {
		
		PaymentRecord save = this.payRepository.save(payment);
		return save;
	}	
}
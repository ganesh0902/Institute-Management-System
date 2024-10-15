package com.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pay.serviceImpl.ServiceImpl;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private ServiceImpl service;
	
	@GetMapping("/")
	public ResponseEntity<String> createOrder() throws RazorpayException
	{
		System.out.println("Create Order Controller Called");
		String createOrder = this.service.createOrder(200);
		
		return new ResponseEntity<String>(createOrder,HttpStatus.OK);
	}
}
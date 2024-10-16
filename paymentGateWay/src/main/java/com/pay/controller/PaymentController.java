package com.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pay.entities.OrderEntities;
import com.pay.serviceImpl.ServiceImpl;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*") 
public class PaymentController {

	@Autowired
	private ServiceImpl service;
	
	@GetMapping("/{amount}")
	public ResponseEntity<OrderEntities> createOrder(@PathVariable("amount") int amount) throws RazorpayException
	{
		System.out.println("Create Order Controller Called");
		 OrderEntities createOrder = this.service.createOrder(amount);
		
		return new ResponseEntity<OrderEntities>(createOrder,HttpStatus.OK);
	}
}
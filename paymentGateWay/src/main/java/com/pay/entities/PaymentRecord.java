package com.pay.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pid;
	private String razorpay_order_id;
	private String razorpay_payment_id;
	private String razorpay_signature;
}

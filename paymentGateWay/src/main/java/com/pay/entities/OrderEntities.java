package com.pay.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="createOrder")
public class OrderEntities {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long gId;
	private String id;	
	private String entity;
	private int amount;
	private int amount_paid;
	private int amount_due;
	private String currency;
	private String receipt;
	private Date  created_at;
	private String offer_id;
	private String status;
	private int stdId;	
}

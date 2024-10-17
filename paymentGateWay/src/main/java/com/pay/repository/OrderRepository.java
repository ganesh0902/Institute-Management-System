package com.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pay.entities.OrderEntities;

public interface OrderRepository extends JpaRepository<OrderEntities, Integer>{

}
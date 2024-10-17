package com.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pay.entities.PaymentRecord;

public interface PaymentRecordRepossitory extends JpaRepository<PaymentRecord, Integer>{

}

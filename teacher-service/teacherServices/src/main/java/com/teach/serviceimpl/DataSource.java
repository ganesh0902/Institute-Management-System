package com.teach.serviceimpl;

import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.activation.DataSource;

class DataCource{
	
	@Bean
	public DataSource dataSource() {
	    HikariDataSource dataSource = new HikariDataSource();
	    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // or whatever your driver is
	    dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/institute_management_system?allowPublicKeyRetrieval=true&useSSL=false");
	    dataSource.setUsername("root");
	    dataSource.setPassword("root123");
	    return dataSource();
	}

}
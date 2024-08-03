package com.identity.service;

import java.util.List;

import com.identity.entity.UserCredential;

public interface ServiceDao {
	public List<UserCredential> getAllTeacher(int instituteId);
}

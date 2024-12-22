package com.identity.service;

import java.util.List;

import com.identity.dto.TeacherDto;
import com.identity.entity.UserCredential;
import com.identity.exception.ResourceNotFoundException;

public interface ServiceDao {
	public List<TeacherDto> getAllTeacher(int instituteId);
	public UserCredential getUserInfo(String email) throws ResourceNotFoundException;
}

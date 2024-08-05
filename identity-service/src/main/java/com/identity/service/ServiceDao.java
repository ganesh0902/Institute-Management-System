package com.identity.service;

import java.util.List;

import com.identity.dto.TeacherDto;
import com.identity.entity.UserCredential;

public interface ServiceDao {
	public List<TeacherDto> getAllTeacher(int instituteId);
}

package com.teach.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teach.dto.TeacherDto;
import com.teach.entities.Teacher;
import com.teach.exception.ResourceNotFoundException;

public interface TeacherService {

	public Teacher saveTeacher(Teacher teacher);	
	public TeacherDto getTeacherById(int id) throws ResourceNotFoundException;
	public List<TeacherDto> getAll();
	public boolean delete(int id) throws ResourceNotFoundException;	
}

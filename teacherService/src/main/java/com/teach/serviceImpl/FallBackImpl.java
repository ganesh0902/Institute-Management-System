package com.teach.serviceImpl;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.teach.dto.TeacherDto;
import com.teach.service.FallBackServices;

@Component
public class FallBackImpl implements FallBackServices{

	@Override
	public TeacherDto batchForFallBack(int id) {
	
		
		TeacherDto fallbackTeacher = new TeacherDto();
	    fallbackTeacher.setTId(id);
	    fallbackTeacher.setFirstName("Fallback Batch Service is not available ");
	    fallbackTeacher.setLastName("User");
	    fallbackTeacher.setEmail("fallback@example.com");
	    fallbackTeacher.setEducation("N/A");
	    fallbackTeacher.setContact("N/A");
	    fallbackTeacher.setImage("fallback.png");
	    fallbackTeacher.setBatchDto(Collections.emptyList());
	    
		return fallbackTeacher;
	}
}
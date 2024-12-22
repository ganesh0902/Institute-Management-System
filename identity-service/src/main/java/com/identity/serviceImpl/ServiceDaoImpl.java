package com.identity.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.identity.dto.TeacherDto;
import com.identity.entity.UserCredential;
import com.identity.exception.ResourceNotFoundException;
import com.identity.repository.UserCredentialRepository;
import com.identity.service.ServiceDao;

@Service
public class ServiceDaoImpl implements ServiceDao {

	@Autowired
	private UserCredentialRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<TeacherDto> getAllTeacher(int instituteId) {

		List<UserCredential> allTeacher = this.repository.getAllTeacher(instituteId);

		ArrayList<TeacherDto> teachers = new ArrayList<>();

		for (UserCredential credential : allTeacher) {
			TeacherDto teacherDto = this.restTemplate
					.getForObject("http://teacher-service/teacher/credential/" + credential.getId(), TeacherDto.class);

			if (teacherDto != null) {
				teachers.add(teacherDto);
			}
		}
		return teachers;
	}
	@Override
	public UserCredential getUserInfo(String email) throws ResourceNotFoundException
	{
		return this.repository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User","Username",email));
	}
	
	
}
package com.identity.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.identity.dto.TeacherDto;
import com.identity.entity.UserCredential;
import com.identity.exception.ResourceNotFoundException;
import com.identity.repository.UserCredentialRepository;
import com.identity.service.ServiceDao;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ServiceDaoImpl implements ServiceDao {

	@Autowired
	private UserCredentialRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<TeacherDto> getAllTeacher(int instituteId) {

		System.out.println("Institute Id "+instituteId);
		List<UserCredential> allTeacher = this.repository.getAllTeacher(instituteId);

		System.out.println("All Teacher");
		System.out.println(allTeacher);
		ArrayList<TeacherDto> teachers = new ArrayList<>();	
		System.out.println("In identity Service");

		for (UserCredential credential : allTeacher) {
			//TeacherDto teacherDto = this.restTemplate
					//.getForObject("http://teacher-service/teacher/credential/" + credential.getId(), TeacherDto.class);					
						
			TeacherDto teacherDto = this.restTemplate.exchange(
					"http://teacher-service/teacher/credential/" + credential.getId(),
					HttpMethod.GET,
					getToken(),
					TeacherDto.class).getBody();
			
			
//			ResponseEntity<String> jsonResponse = restTemplate.exchange(
//					url,
//					HttpMethod.GET,
//					getToken(),
//					String.class
//					);	
			if (teacherDto != null) {
				teachers.add(teacherDto);
			}
		}
		return teachers;
	}

	@Override
	public UserCredential getUserInfo(String email) throws ResourceNotFoundException {
		return this.repository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Username", email));
	}

	private HttpEntity<String> getToken() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			String authHeader = request.getHeader("Authorization");

			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", authHeader);
			HttpEntity<String> entity = new HttpEntity<>(headers);

			return entity;
		}
		return null;
	}

	@Override
	public UserCredential getUserById(int userId) {
		// TODO Auto-generated method stub
		Optional<UserCredential> byId = this.repository.findById(userId);
		return byId.get();
	}

	@Override
	public List<UserCredential> getAllUser() {
		 List<UserCredential> all = this.repository.findAll();
			
		return all;
	}
}
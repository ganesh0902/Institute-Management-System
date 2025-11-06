package com.std.serviceImpl;

import org.springframework.cache.annotation.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.std.dto.BatchDto;
import com.std.dto.CourseDto;
import com.std.dto.StudentDto;
import com.std.dto.TeacherDto;
import com.std.entities.Student;
import com.std.exception.ResourceNotFoundException;
import com.std.exception.ServiceFailureException;
import com.std.repository.StudentRepository;
import com.std.service.Service;

import jakarta.servlet.http.HttpServletRequest;

@org.springframework.stereotype.Service
public class StudentImpl implements Service {

	@Autowired
	private StudentRepository repo;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@CachePut(cacheNames = "student", key = "#std.stdId")
	public Student saveStudent(Student std) {

		BatchDto batch;
		CourseDto course;

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", getToken());
		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
//		batch = this.restTemplate.getForObject("http://batch-service/batch/" + std.getBatchId(),			
//				BatchDto.class);

			String batchUrl = "http://batch-service/batch/" + std.getBatchId();

			batch = this.restTemplate.exchange(batchUrl, HttpMethod.GET, entity, BatchDto.class).getBody();

		} catch (Exception e) {
			throw new ServiceFailureException("Failed to fetch batch information");
		}

		try {
//			course = this.restTemplate.getForObject("http://course-service/course/" + batch.getCourseId(),
//					CourseDto.class);

//			String courseUrl = "http://course-service/course/" + batch.getCourseId();
//			
//			course = this.restTemplate.exchange(
//					courseUrl,
//					HttpMethod.GET,
//					entity,
//					CourseDto.class).getBody();
		} catch (Exception e) {
			throw new ServiceFailureException("Failed to fetch Course information");
		}

		// String fees = course.getFees();
		// std.setCourseName(course.getCourseName());
		// Long totalFees = Long.parseLong(fees);
		// std.setTotalFees(totalFees);
		return this.repo.save(std);
	}

	@Override
	@CachePut(cacheNames = "student", key = "#std.stdId")
	public Student updateStudent(int stdId, Student std) throws ResourceNotFoundException {

		Student student = this.repo.findById(stdId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(stdId)));
		student.setBatchId(std.getBatchId() == 0 ? student.getBatchId() : std.getBatchId());
		student.setCourseName(std.getCourseName());
		student.setFirstName(std.getFirstName());
		student.setLastName(std.getLastName());
		student.setLastEducation(std.getLastEducation());
		student.setPassoutYear(std.getPassoutYear());
		return this.repo.save(student);
	}

	@Override
	//@Cacheable(cacheNames = "student", key = "#stdId")
	public Student findById(int stdId) throws ResourceNotFoundException {

		System.out.println("Getting student record from dataBase");
		 Student student = this.repo.findById(stdId)
			.orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(stdId)));
		 
		 System.out.println(student);
		return student;
	}

	@Override
	// @Cacheable(cacheNames = "teacher", key = "#instituteId")
	public List<Student> getAll(long instituteId) {

		System.out.println("Getting student by instituteId");
		System.out.println(repo.findByInstituteId(instituteId));
		return repo.findByInstituteId(instituteId);
	}

	@Override
	@CacheEvict(cacheNames = "student", key = "#stdId")
	public boolean delete(int stdId) throws ResourceNotFoundException {

		boolean status = false;

		Student student = this.repo.findById(stdId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(stdId)));

		if (student != null) {
			status = true;

			this.repo.deleteById(stdId);
		}
		return status;
	}

	@Override
	public List<Student> getStudentByFilter(String studentName) {

		List<Student> studentFilter = this.repo.findByFirstNameContaining(studentName);

		return studentFilter;
	}

	@Override
	@Cacheable(cacheNames = "student", key = "#stdId")
	public StudentDto getStudentDetails(int stdId) throws ResourceNotFoundException {

		StudentDto studentDto = new StudentDto();
		BatchDto batch = null;
		TeacherDto teacher = null;
		CourseDto course = null;

		Student student = this.repo.findById(stdId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(stdId)));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", getToken());
		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {

			String batchURl = "http://batch-service/batch/" + student.getBatchId();
//			// get batch details from it's service by batchId

			batch = this.restTemplate.exchange(batchURl, HttpMethod.GET, entity, BatchDto.class).getBody();

			// get teacher details from it's service by teacherId
			String teacherUrl = "http://teacher-service/teacher/" + batch.getTeacherId();

			teacher = this.restTemplate.exchange(teacherUrl, HttpMethod.GET, entity, TeacherDto.class).getBody();

			String courseUrl = "http://course-service/course/" + batch.getCourseId();

			course = this.restTemplate.exchange(courseUrl, HttpMethod.GET, entity, CourseDto.class).getBody();

			System.out.println("Course is " + course);
		} catch (Exception e) {
			System.out.println("Error occurred In Student details service: {}: " + e.getMessage());
			throw new ServiceFailureException("Failed to fetch course information");
		}

		studentDto.setStdId(student.getStdId());
		studentDto.setFirstName(student.getFirstName());
		studentDto.setLastName(student.getLastName());
		studentDto.setPassoutYear(student.getPassoutYear());
		studentDto.setLastEducation(student.getLastEducation());
		studentDto.setCourseName(student.getCourseName());
		studentDto.setImage(student.getImage());
		studentDto.setTeacherDto(teacher);
		batch.setCourseDto(course);
		studentDto.setBatchDto(batch);
		return studentDto;
	}

	@Override
	@Cacheable(cacheNames = "student", key = "#instituteId")
	public Long courseStudent(long instituteId) {

		return this.repo.countStudentByInstituteId(instituteId);
	}

	@Override
	//@Cacheable(cacheNames = "student", key = "#batchId")
	public List<Student> getAllStudentByBatch(int batchId) {

		System.out.println("Getting student by batch Id from database");
		Optional<List<Student>> allStudentByBatchId = this.repo.getAllStudentByBatchId(batchId);
		
		System.out.println("SSTDStudent"+allStudentByBatchId);
		//System.out.println(allStudentByBatchId);
		
//		List<Student> students = null;
//		if (allStudentByBatchId.isPresent()) {
//			students = allStudentByBatchId.get();
//		} else {
//			System.out.println("Record not found");
//		}
		
		
		return allStudentByBatchId.get();
	}

	@Override
	@Cacheable(cacheNames = "student")
	public List<Student> getAllStudent() {

		System.out.println("Getting All Student from dataBase");
		List<Student> findAll = this.repo.findAll();
		return findAll;
	}

	@Override
	public List<Student> getStudentByTeacherId(int tId) {

		List<Student> studentList = new ArrayList<>();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", getToken());
		HttpEntity<String> entity = new HttpEntity<>(headers);

		String url = "http://batch-service/batch/batchByTeacherId/" + tId;

		List<BatchDto> batchList = this.restTemplate
				.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<BatchDto>>() {
				}).getBody();

		System.out.println("BatchList"+batchList);
		for (BatchDto batch : batchList) {
			List<Student> findByTeacherId = this.repo.findByTeacherId(batch.getBid());
			studentList.addAll(findByTeacherId);
		}

		return studentList;
	}

	private String getToken() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			String authHeader = request.getHeader("Authorization");
			return authHeader; // e.g., "Bearer eyJhbGciOiJIUzI1NiIs..."
		}
		return null;
	}

	@Override
	public int getTotalStudent() {

		return this.repo.getTotalStudents();
	}
}
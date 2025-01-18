package com.std.serviceImpl;

import org.springframework.cache.annotation.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import com.std.dto.BatchDto;
import com.std.dto.CourseDto;
import com.std.dto.StudentDto;
import com.std.dto.TeacherDto;
import com.std.entities.Student;
import com.std.exception.ResourceNotFoundException;
import com.std.exception.ServiceFailureException;
import com.std.repository.StudentRepository;
import com.std.service.Service;

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
		
		try
		{
		batch = this.restTemplate.getForObject("http://batch-service/batch/" + std.getBatchId(),
				BatchDto.class);
		}
		catch(Exception e)
		{
			throw new ServiceFailureException("Failed to fetch batch information");
		}
		
		try
		{
			course = this.restTemplate.getForObject("http://course-service/course/" + batch.getCourseId(),
					CourseDto.class);
		}
		catch(Exception e)
		{
			throw new ServiceFailureException("Failed to fetch Course information"); 
		}
		

		String fees = course.getFees();
		std.setCourseName(course.getCourseName());
		Long totalFees = Long.parseLong(fees);
		std.setTotalFees(totalFees);
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
	@Cacheable(cacheNames = "student", key = "#stdId")
	public Student findById(int stdId) throws ResourceNotFoundException {

		System.out.println("Getting student record from dataBase");
		return this.repo.findById(stdId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(stdId)));
	}

	@Override
	@Cacheable(cacheNames = "teacher", key = "#instituteId")
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
		CourseDto course =null;
				
		Student student = this.repo.findById(stdId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(stdId)));
				
		try
		{
			// get batch details from it's service by batchId
			batch = this.restTemplate.getForObject("http://batch-service/batch/" + student.getBatchId(),
					BatchDto.class);
		}
		catch(Exception e)
		{
			System.out.println("Error occurred while calling batch-service: {}: "+ e.getMessage());
			throw new ServiceFailureException("Failed to fetch batch information");
		}

		try
		{
			// get teacher details from it's service by teacherId			
			teacher = this.restTemplate.getForObject("http://teacher-service/teacher/" + batch.getTeacherId(),
					TeacherDto.class);
		}
		catch(Exception e)
		{
			System.out.println("Error occurred while calling teacher-service: {}: "+ e.getMessage());
			throw new ServiceFailureException("Failed to fetch teacher information");
		}

		try
		{
			// get course details from it's service by course Id			
			course = this.restTemplate.getForObject("http://course-service/course/" + batch.getCourseId(),
					CourseDto.class);
		}
		catch(Exception e)
		{
			System.out.println("Error occurred while calling course-service: {}: "+ e.getMessage());
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
	@Cacheable(cacheNames = "student", key = "#batchId")
	public List<Student> getAllStudentByBatch(int batchId) {

		System.out.println("Getting student by batch Id from database");
		Optional<List<Student>> allStudentByBatchId = this.repo.getAllStudentByBatchId(batchId);
		List<Student> students = null;
		if (allStudentByBatchId.isPresent()) {
			students = allStudentByBatchId.get();
		} else {
			System.out.println("Record not found");
		}
		return students;
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
		String url = "http://batch-service/batch/batchByTeacherId/" + tId;

		ResponseEntity<List<BatchDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BatchDto>>() {
				});

		System.out.println("Batch");
		System.out.println(responseEntity.getBody());
		List<BatchDto> batchList = responseEntity.getBody();

		for (BatchDto batch : batchList) {
			List<Student> findByTeacherId = this.repo.findByTeacherId(batch.getBid());
			studentList.addAll(findByTeacherId);
		}

		return studentList;
	}

}

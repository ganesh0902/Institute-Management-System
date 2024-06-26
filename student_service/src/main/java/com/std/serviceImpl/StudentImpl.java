package com.std.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import com.std.dto.BatchDto;
import com.std.dto.CourseDto;
import com.std.dto.StudentDto;
import com.std.dto.TeacherDto;
import com.std.entities.Student;
import com.std.exception.ResourceNotFoundException;
import com.std.repository.StudentRepository;
import com.std.service.Service;

@org.springframework.stereotype.Service
public class StudentImpl implements Service {

	@Autowired
	private StudentRepository repo;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Student saveStudent(Student std) {

		return this.repo.save(std);
	}

	@Override
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
	public Student findById(int stdId) throws ResourceNotFoundException {

		return this.repo.findById(stdId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(stdId)));
	}

	@Override
	public List<Student> getAll() {

		return repo.findAll();
	}

	@Override
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
	public StudentDto getStudentDetails(int stdId) throws ResourceNotFoundException {

		StudentDto studentDto = new StudentDto();

		Student student = this.repo.findById(stdId)
				.orElseThrow(() -> new ResourceNotFoundException("Student", "Id", String.valueOf(stdId)));

		// get batch details from it's service by batchId
		BatchDto batch = this.restTemplate.getForObject("http://batch-service/batch/" + student.getBatchId(),
				BatchDto.class);

		// get teacher details from it's service by teacherId
		TeacherDto teacher = this.restTemplate.getForObject("http://teacher-service/teacher/" + batch.getTeacherId(),
				TeacherDto.class);

		// get course details from it's service by course Id
		CourseDto course = this.restTemplate.getForObject("http://course-service/course/" + batch.getCourseId(),
				CourseDto.class);

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
	public Long courseStudent(long instituteId) {

		return this.repo.countStudentByInstituteId(instituteId);
	}
}

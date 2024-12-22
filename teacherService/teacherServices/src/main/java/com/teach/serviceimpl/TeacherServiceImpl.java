package com.teach.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.teach.dto.TeacherDto;
import com.teach.dto.TeacherIdAndName;
import com.teach.entities.Teacher;
import com.teach.exception.ResourceNotFoundException;
import com.teach.repository.TeacherRepository;
import com.teach.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherRepository repository;
	
	@Override
	@CachePut(cacheNames = "teacher", key = "#teacher.tId")
	public Teacher saveTeacher(Teacher teacher) {
		return this.repository.save(teacher);
	}

	@Override
	@Cacheable(cacheNames = "teacher", key = "#tId")
	public TeacherDto getTeacherById(int tId) throws ResourceNotFoundException {
		
		TeacherDto teacherDto = new TeacherDto();
		Teacher teacher = this.repository.findById(tId)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher", "Id", String.valueOf(tId)));
		teacherDto.setTId(teacher.getTId());
		teacherDto.setFirstName(teacher.getFirstName());
		teacherDto.setLastName(teacher.getLastName());
		teacherDto.setEmail(teacher.getEmail());
		teacherDto.setEducation(teacher.getEducation());
		teacherDto.setContact(teacher.getContact());
		teacherDto.setImage(teacher.getImage());

		// this.restTemplate.getForObject("http://batch-service/batch/teacherId/"+teacher.getTId(),BatchDto.class);

		return teacherDto;
	}

	@Override									  
	/* @Cacheable(cacheNames = "teacher", key = "#instituteId") */
	public List<TeacherDto> getAll(long instituteId) {

		List<Teacher> allList = this.repository.findAllByInstitute(instituteId);
		List<TeacherDto> teacherDtoList = new ArrayList<>();

		System.out.println("Getting record from data base");
		for (Teacher teacher : allList) {

			TeacherDto teacherDto = new TeacherDto();
			teacherDto.setFirstName(teacher.getFirstName());
			teacherDto.setLastName(teacher.getLastName());
			teacherDto.setContact(teacher.getContact());
			teacherDto.setEducation(teacher.getEducation());
			teacherDto.setEmail(teacher.getEmail());
			teacherDto.setTId(teacher.getTId());
			teacherDto.setImage(teacher.getImage());
			// this.restTemplate.getForObject("http://batch-service/batch/teacher/"+teacher.getTId(),BatchDto.class);
			// teacherDto.setBatchDto(batchDto);
			teacherDtoList.add(teacherDto);

		}
		return teacherDtoList;
	}

	@Override
	@CacheEvict(cacheNames = "teacher",key="#tId")
	public boolean delete(int tId) throws ResourceNotFoundException {

		boolean status = false;
		Teacher teacher = this.repository.findById(tId)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher", "Id", String.valueOf(tId)));

		if (teacher != null) {
			status = true;
		}
		return status;
	}

	@Override
	@Cacheable(cacheNames = "teacher", key = "#instituteId")
	public List<TeacherIdAndName> getTeacherIdAndName(long instituteId) {

		List<Object[]> teacherIdAndName = this.repository.getTeacherIdAndName(instituteId);

		System.out.println("Getting record from data base");
		return teacherIdAndName.stream().map(teacher -> {

			TeacherIdAndName teacherr = new TeacherIdAndName();
			Object teacherId = teacher[0];
			Object teacherName = teacher[1];

			int teacherid = (int) teacherId;
			String teachername = teacherName.toString();

			teacherr.setTeacherId(teacherid);
			teacherr.setTeacherName(teachername);

			return teacherr;
		}).collect(Collectors.toList());
	}

	@Override
	@Cacheable(cacheNames = "teacher", key = "#instituteId")
	public long getTeacherCount(Long instituteId) {
		System.out.println("Fetching data from database");
		return this.repository.TeacherCount(instituteId);
	}

	@Override
	@CachePut(cacheNames = "teacher", key = "#tId")
	public Teacher updateTeacher(int tId, Teacher teacherDto) throws ResourceNotFoundException {

		System.out.println("Updating from Database");
		Teacher teacher = this.repository.findById(tId)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher", "Id", String.valueOf(tId)));

		teacher.setContact(teacherDto.getContact() == "" ? teacher.getContact() : teacherDto.getContact());
		teacher.setEducation(teacherDto.getEducation() == "" ? teacher.getEducation() : teacherDto.getEducation());
		teacher.setFirstName(teacherDto.getFirstName() == "" ? teacher.getFirstName() : teacherDto.getFirstName());
		teacher.setLastName(teacherDto.getLastName() == "" ? teacher.getLastName() : teacherDto.getLastName());

		return this.repository.save(teacher);
	}

	@Override	
	public Teacher getTeacherByCredential(int cId) {

		System.out.println(cId);
		return this.repository.getTeacherByCredential(cId);

	}
}
package com.teach.serviceimpl;

import java.util.ArrayList;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.teach.dto.BatchDto;
import com.teach.dto.TeacherDto;
import com.teach.entities.Teacher;
import com.teach.exception.ResourceNotFoundException;
import com.teach.repository.TeacherRepository;
import com.teach.service.TeacherService;


@Service
public class TeacherServiceImpl implements TeacherService{

	@Autowired
	private TeacherRepository repository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Teacher saveTeacher(Teacher teacher) {
		return this.repository.save(teacher);				
	}
	@Override
	public TeacherDto getTeacherById(int id) throws ResourceNotFoundException {
				
		TeacherDto teacherDto = new TeacherDto();		
		Teacher teacher = this.repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Teacher","Id",String.valueOf(id)));		
		teacherDto.setTId(teacher.getTId());
		teacherDto.setFirstName(teacher.getFirstName());
		teacherDto.setLastName(teacher.getLastName());
		teacherDto.setEmail(teacher.getEmail());
		teacherDto.setEducation(teacher.getEducation());
		teacherDto.setContact(teacher.getContact());					 
		
		//this.restTemplate.getForObject("http://batch-service/batch/teacherId/"+teacher.getTId(),BatchDto.class);
		
		return teacherDto;
	}

	@Override
	public List<TeacherDto> getAll() {
		
		List<Teacher> allList = this.repository.findAll();
		List<TeacherDto> teacherDtoList=new ArrayList<>();
		
		for (Teacher teacher : allList) {
			
			TeacherDto teacherDto = new TeacherDto();
			teacherDto.setFirstName(teacher.getFirstName());
			teacherDto.setLastName(teacher.getLastName());
			teacherDto.setContact(teacher.getContact());
			teacherDto.setEducation(teacher.getEducation());
			teacherDto.setEmail(teacher.getEmail());
			teacherDto.setTId(teacher.getTId());			
			 //this.restTemplate.getForObject("http://batch-service/batch/teacher/"+teacher.getTId(),BatchDto.class);			
			//teacherDto.setBatchDto(batchDto);
			teacherDtoList.add(teacherDto);
			
		}					
		return teacherDtoList;
	}

	@Override
	public boolean delete(int id) throws ResourceNotFoundException {
		
		boolean status=false;
		Teacher teacher = this.repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Teacher","Id",String.valueOf(id)));				
		
		if(teacher!=null)
		{
			status=true;
		}		
		return status;
	}
	
}
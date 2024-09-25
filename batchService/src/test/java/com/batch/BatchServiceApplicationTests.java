package com.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.batch.batchImpl.BatchServiceImpl;
import com.batch.dto.BatchDto;
import com.batch.dto.TeacherDto;
import com.batch.entities.Batch;
import com.batch.entities.Course;
import com.batch.exception.ResourceNotFoundException;
import com.batch.repository.BatchRepository;
import com.batch.service.batchService;

@SpringBootTest
class BatchServiceApplicationTests {
	
	@Mock
	private BatchRepository repository;
	
	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private BatchServiceImpl serviceImpl;
	
	@Test
	void contextLoads() {
		
	}
	
	@Test
    public void testGetBatchSuccess() throws ResourceNotFoundException {
		
		 int bId = 1;

	        Batch batch = new Batch();
	        batch.setBId(bId);
	        batch.setBatchTitle("Java Batch");
	        batch.setDuration("6 Months");
	        batch.setStartDate("");
	        batch.setEndDate("");
	        batch.setStatus("");
	        batch.setLocation("");
	        batch.setTime("");
	        batch.setImage("");
	        batch.setTeacherId(10);
	        batch.setCourseId(21);
	        batch.setInstituteId(21l);
	        
	        Mockito.when(repository.findById(bId)).thenReturn(Optional.of(batch));
	        
	        Course mockCourse = new Course();
	        mockCourse.setCid(10);
	        mockCourse.setCourseName("Java");
	        mockCourse.setDescription("This course is for Java Developer");
	        mockCourse.setFees("2022");
	        mockCourse.setSkills("Java, Python");
	        
	        
	        TeacherDto teacherDto = new TeacherDto();
	        teacherDto.setTId(21);
	        teacherDto.setFirstName("Ganesh");
	        teacherDto.setLastName("Sakhare");
	        teacherDto.setEducation("BCA");
	        teacherDto.setContact("9595956150");
	        teacherDto.setEmail("ganeshs2987@gmail.com");
	        teacherDto.setEmail("aaa.jpg");
	        	        
	        //Mock The restTemplate
	        Mockito.when(restTemplate.getForObject("http://course-service/course/"+batch.getCourseId(),Course.class)).thenReturn(mockCourse);
	        
	        Mockito.when(restTemplate.getForObject("http://teacher-service/teacher/"+batch.getTeacherId(), TeacherDto.class)).thenReturn(teacherDto);
	        	        
	        //act
	        BatchDto batchResult = serviceImpl.getBatch(bId);
	        
	        assertNotNull(batchResult);
	        assertEquals("Java Batch", batchResult.getBatchTitle());
	        assertEquals("Java", batchResult.getCourse().getCourseName());
	        assertEquals("Ganesh", batchResult.getTeacherDto().getFirstName());
	        	        
	        Mockito.verify(repository,times(1)).findById(bId);
	        Mockito.verify(restTemplate,times(1)).getForObject("http://course-service/course/" +batch.getCourseId(), Course.class);
	        Mockito.verify(restTemplate, times(1)).getForObject("http://teacher-service/teacher/" + batch.getTeacherId(), TeacherDto.class);
	}
	@Test()
	public void testBatchNotFound()
	{
		 int bId=21;
		 
		 when(repository.findById(bId)).thenReturn(Optional.empty());
		 
		 assertThrows(ResourceNotFoundException.class, ()-> serviceImpl.getBatch(bId));
		
	}	
}
package com.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.config.ConfigData.Options;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Optionals;
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
	
	private Batch existingBatch;
	private Batch updateBatch;
	
	@Test
	void contextLoads() {
				
		  
	}
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);

        // Set up existing batch
        existingBatch = new Batch();
        existingBatch.setBatchTitle("Original Title");
        existingBatch.setDuration("6 months");
        existingBatch.setStartDate("2024-01-01");
        existingBatch.setEndDate("2024-06-30");
        existingBatch.setStatus("Active");
        existingBatch.setTeacherId(1);
        existingBatch.setTime("10:00 AM");

        // Set up update batch
        updateBatch = new Batch();
        updateBatch.setBatchTitle("Updated Title");
        updateBatch.setDuration("3 months");
        updateBatch.setStartDate("2024-02-01");
        updateBatch.setEndDate("2024-05-30");
        updateBatch.setStatus("Inactive");
        updateBatch.setTeacherId(2);
        updateBatch.setTime("02:00 PM");
		
	}
	
	@Test
    public void testGetexistBatchSuccess() throws ResourceNotFoundException {
		
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
	@Test
	public void saveBatch()
	{
		Batch batch = new Batch();
		
		batch.setBId(10);
		batch.setBatchTitle("Java Development");
		batch.setCourseId(10);
		batch.setDuration("4 Months");
		batch.setStartDate("09-01-2009");
		batch.setEndDate("09-08-2009");
		batch.setImage("");		
		batch.setStatus("pending");
		batch.setTeacherId(101);
		batch.setTime("09:20");
		
		Mockito.when(repository.save(batch)).thenReturn(batch);
		
		Batch result = this.serviceImpl.saveBatch(batch);
		
		assertNotNull(result);
		assertEquals(batch,result);		
		
		verify(repository, times(1)).save(batch);
	}

	@Test
	public void updateBatch() throws ResourceNotFoundException
	{
		int batchId =21;
		
		Mockito.when(this.repository.findById(batchId)).thenReturn(Optional.of(updateBatch));
		Mockito.when(this.repository.save(updateBatch)).thenReturn(updateBatch);
		
		Batch updatedBatch = this.serviceImpl.updateBatch(batchId, updateBatch);
		
		 // Verify that the fields are updated correctly
        assertEquals("Updated Title", updatedBatch.getBatchTitle());
        assertEquals("3 months", updatedBatch.getDuration());
        assertEquals("2024-02-01", updatedBatch.getStartDate());
        assertEquals("2024-05-30", updatedBatch.getEndDate());
        assertEquals("Inactive", updatedBatch.getStatus());
        assertEquals(2, updatedBatch.getTeacherId());
        assertEquals("02:00 PM", updatedBatch.getTime());

        // Verify that save was called
        verify(repository, times(1)).save(updatedBatch);		
	}
	@Test
	public void testUpdateBatch_ResourceNotFoundException()
	{
		int batchId = 21;
		
		Mockito.when(this.repository.findById(batchId)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, ()-> {
			serviceImpl.updateBatch(batchId, updateBatch);
		});
		
		verify(repository, never()).save(any(Batch.class));
	}
	@Test
	public void testDeleteBatch() throws ResourceNotFoundException
	{
		int bId =21;
		
		Mockito.when(this.repository.findById(bId)).thenReturn(Optional.of(this.existingBatch));
		
		boolean result = serviceImpl.delete(bId);
		
		assertEquals(true, result);
		
		verify(repository, times(1)).findById(bId);
		verify(repository, times(1)).deleteById(bId);		
	}
	@Test
	public void testDeleteBatch_ResourceNotFoundException()
	{
		int batchId = 21;
		
		Mockito.when(this.repository.findById(batchId)).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, ()->{
			
			this.serviceImpl.delete(batchId);
		});
		
		verify(repository,times(1)).findById(batchId);
	}
	
}
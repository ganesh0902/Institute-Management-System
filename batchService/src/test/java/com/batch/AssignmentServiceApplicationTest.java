package com.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.batch.batchImpl.AssignmentImpl;
import com.batch.entities.Assignment;
import com.batch.exception.ResourceNotFoundException;
import com.batch.repository.AssignmentRepository;

@SpringBootTest
public class AssignmentServiceApplicationTest {

//	@Mock
//	private AssignmentRepository repositoty;
//
//	@InjectMocks
//	private AssignmentImpl serviceImpl;
//
//	@Mock
//	private RestTemplate restTemplate;
//
//	private Assignment assignment;
//
//	@BeforeEach
//	void contextLoads() {
//
//		assignment = new Assignment();
//		assignment.setTaskId(1);
//		assignment.setTitle("Test Title");
//		assignment.setDescription("Test Description");
//		assignment.setStartDate("2024-10-02");
//		assignment.setEndDate("2024-10-10");
//		assignment.setBatchId(101);
//		assignment.setTime("10:00 AM");
//		assignment.setStatus("Pending");
//		assignment.setTeacherId(1001);
//	}
//
//	@Test
//	public void testSaveAssignment() {
//		Assignment mockAssignment = new Assignment();
//		mockAssignment.setTitle("Java Inheritance");
//		mockAssignment.setDescription("what is java");
//		mockAssignment.setStartDate("09-08-2009");
//		mockAssignment.setEndDate("15-08-2009");
//		mockAssignment.setBatchId(21);
//		mockAssignment.setTime("09:20");
//		mockAssignment.setTeacherId(23);
//
//		Assignment expected = new Assignment();
//		expected.setTitle("Java Inheritance");
//		expected.setDescription("what is java");
//		expected.setStartDate("09-08-2009");
//		expected.setEndDate("15-08-2009");
//		expected.setBatchId(21);
//		expected.setTime("09:20");
//		expected.setTeacherId(23);
//		expected.setStatus("active");
//
//		Mockito.when(this.repositoty.save(mockAssignment)).thenReturn(expected);
//
//		Assignment result = this.serviceImpl.saveAssignment(mockAssignment);
//
//		assertNotNull(result);
//		assertEquals(expected, result);
//		assertEquals("active", result.getStatus());
//
//		verify(this.repositoty, times(1)).save(mockAssignment);
//	}
//
//	@Test
//	public void testSaveAssignment_runtimeException() {
//		Assignment mockAssignment = new Assignment();
//		mockAssignment.setTitle("Java Inheritance");
//		mockAssignment.setDescription("what is java");
//		mockAssignment.setStartDate("09-08-2009");
//		mockAssignment.setEndDate("15-08-2009");
//		mockAssignment.setBatchId(21);
//		mockAssignment.setTime("09:20");
//		mockAssignment.setTeacherId(23);
//
//		Mockito.when(this.repositoty.save(mockAssignment)).thenThrow(new RuntimeException("Database Error"));
//
//		RuntimeException result = assertThrows(RuntimeException.class, () -> {
//			this.serviceImpl.saveAssignment(mockAssignment);
//		});
//
//		assertEquals("Database Error", result.getMessage());
//	}
//
//	@Test
//	public void testGetAssignmentById() throws ResourceNotFoundException {
//		int tastId = 1;
//
//		Mockito.when(this.repositoty.findById(tastId)).thenReturn(Optional.of(assignment));
//
//		Assignment result = this.serviceImpl.getAssignmentById(tastId);
//
//		assertNotNull(result);
//		assertEquals(assignment, result);
//		assertEquals(1, result.getTaskId());
//		verify(this.repositoty, times(1)).findById(tastId);
//	}
//
//	@Test
//	public void testGetAssignmentById_resourceNotFoundException() {
//		int taskId = 12;
//
//		Mockito.when(this.repositoty.findById(taskId)).thenReturn(Optional.empty());
//
//		ResourceNotFoundException result = assertThrows(ResourceNotFoundException.class, () -> {
//
//			this.serviceImpl.getAssignmentById(taskId);
//		});
//
//		verify(repositoty, times(1)).findById(taskId);
//	}
//
//	@Test
//	public void testGetAssignmentById_runtimeException() {
//		int taskId = 12;
//
//		Mockito.when(this.repositoty.findById(taskId)).thenThrow(new RuntimeException("Database Error"));
//
//		RuntimeException resultException = assertThrows(RuntimeException.class, () -> {
//			this.serviceImpl.getAssignmentById(taskId);
//		});
//
//		assertEquals("Database Error", resultException.getMessage());
//	}
//	@Test
//	public void testGetAllAssignmentByBatch() {
//		int batchId = 101;
//		
//		List<Assignment> singletonList = Collections.singletonList(assignment);
//		
//		Mockito.when(this.repositoty.getAllAssignmentsByBatchid(batchId)).thenReturn(singletonList);
//		
//		List<Assignment> result = this.serviceImpl.getAllAssignmentByBatch(batchId);
//		
//		assertNotNull(result);
//		assertEquals(1, result.size());
//				
//		verify(this.repositoty,times(1)).getAllAssignmentsByBatchid(batchId);
//	}
//	@Test
//	public void testGetAllAssignmentByBatch_emprty()
//	{
//		int batchId = 101;
//		
//		Mockito.when(this.repositoty.getAllAssignmentsByBatchid(batchId)).thenReturn(Collections.EMPTY_LIST);
//		
//		List<Assignment> result = this.serviceImpl.getAllAssignmentByBatch(batchId);
//		
//		assertEquals(result, Collections.EMPTY_LIST);
//		
//		verify(this.repositoty,times(1)).getAllAssignmentsByBatchid(batchId);
//	}
//	@Test
//	public void testGetAllAssignmentByTeacherId()
//	{
//		int teacherId = 12;
//		
//		List<Assignment> singletonList = Collections.singletonList(assignment);
//		
//		Mockito.when(this.repositoty.getAllAssignmentByTeacherId(teacherId)).thenReturn(singletonList);
//		
//	    List<Assignment> result = this.serviceImpl.getAllAssignmentByTeacherId(teacherId);
//	    
//	    assertNotNull(result);
//	    assertEquals(1, result.size());
//	    
//	    verify(this.repositoty,times(1)).getAllAssignmentByTeacherId(teacherId);	   
//	}
//	@Test
//	public void testGetAllAssignmentByTeacherId_empty()
//	{
//		int teacherId = 12;
//				
//		
//		Mockito.when(this.repositoty.getAllAssignmentByTeacherId(teacherId)).thenReturn(Collections.EMPTY_LIST);
//		
//	    List<Assignment> result = this.serviceImpl.getAllAssignmentByTeacherId(teacherId);
//	    
//	    assertNotNull(result);
//	    assertEquals(result, Collections.EMPTY_LIST);
//	    
//	    verify(this.repositoty,times(1)).getAllAssignmentByTeacherId(teacherId);
//	    
//	}
}
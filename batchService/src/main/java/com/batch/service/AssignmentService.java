package com.batch.service;

import java.util.List;

import com.batch.entities.Assignment;
import com.batch.exception.ResourceNotFoundException;

public interface AssignmentService {

	public Assignment saveAssignment(Assignment assignment);
	public Assignment getAssignmentById(int taskId) throws ResourceNotFoundException;
	public List<Assignment> getAllAssignmentByBatch(int batchId);	
}

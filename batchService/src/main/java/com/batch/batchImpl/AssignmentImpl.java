package com.batch.batchImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.batch.entities.Assignment;
import com.batch.exception.ResourceNotFoundException;
import com.batch.repository.AssignmentRepository;
import com.batch.service.AssignmentService;

@Service
public class AssignmentImpl implements AssignmentService {

	@Autowired
	private AssignmentRepository repository;

	@Override
	public Assignment saveAssignment(Assignment assignment) {
		if(assignment.getTaskId()==0)
		{
			assignment.setStatus("active");
		}
		LocalDate currentDate = LocalDate.now();
		assignment.setStartDate(currentDate.toString());
		Assignment save = this.repository.save(assignment);
		return save;
	}

	@Override
	public Assignment getAssignmentById(int taskId) throws ResourceNotFoundException {

		return this.repository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "Id", String.valueOf(taskId)));

	}

	@Override
	public List<Assignment> getAllAssignmentByBatch(int batchId) {
		
		return this.repository.getAllAssignmentsByBatchid(batchId);		
	}

	@Override
	public List<Assignment> getAllAssignmentByTeacherId(int teacherId) {

		return this.repository.getAllAssignmentByTeacherId(teacherId);		
	}
}

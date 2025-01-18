package com.std.fallBack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.std.dto.CourseDto;
import com.std.entities.Student;
import com.std.repository.StudentRepository;

@Component
public class FallBackServices {

	@Autowired
	private StudentRepository repo;
	
	 // Fallback method for batch-service failure
    public Student batchServiceFallback(Student std, Throwable throwable) {
        // Handle failure scenario for batch service, possibly set default values or return saved student
        System.out.println("Batch Service is unavailable: " + throwable.getMessage());
        std.setCourseName("Default Course");
        std.setTotalFees(0L);  // default fee when service is down
        return this.repo.save(std);
    }
       
    public CourseDto courseServiceFallback(Long courseId, Throwable throwable) {
        // Handle failure scenario for course service
        System.out.println("Course Service is unavailable: " + throwable.getMessage());
        return new CourseDto("Default Course", "0"); // Fallback course data
    }
}

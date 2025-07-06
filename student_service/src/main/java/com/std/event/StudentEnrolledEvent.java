package com.std.event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentEnrolledEvent {

	    private Long CourseId;
	 	private Long studentId;	    
	    private Long batchId;
		public StudentEnrolledEvent(Long courseId, Long studentId, Long batchId) {
			super();
			CourseId = courseId;
			this.studentId = studentId;
			this.batchId = batchId;
		}
		
		@Override
		public String toString() {
			return "StudentEnrolledEvent [CourseId=" + CourseId + ", studentId=" + studentId + ", batchId=" + batchId
					+ "]";
		}	    	    	    
}

package com.std.event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentEnrolledEvent {

	    private Long CourseId;
	 	private int studentId;	    
	    private Long batchId;
		public StudentEnrolledEvent(Long courseId, int studentId2, Long batchId) {
			super();
			CourseId = courseId;
			this.studentId = studentId2;
			this.batchId = batchId;
		}
		
		@Override
		public String toString() {
			return "StudentEnrolledEvent [CourseId=" + CourseId + ", studentId=" + studentId + ", batchId=" + batchId
					+ "]";
		}

		public Long getCourseId() {
			return CourseId;
		}

		public void setCourseId(Long courseId) {
			CourseId = courseId;
		}

		public int getStudentId() {
			return studentId;
		}

		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}

		public Long getBatchId() {
			return batchId;
		}

		public void setBatchId(Long batchId) {
			this.batchId = batchId;
		}	    
		
		
}

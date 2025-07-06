package com.std.event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentDroppedEvent {

	 private Long studentId;
	 private String reason;
	public StudentDroppedEvent(Long studentId, String reason) {
		super();
		this.studentId = studentId;
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "StudentDroppedEvent [studentId=" + studentId + ", reason=" + reason + "]";
	}
	 
	 
}
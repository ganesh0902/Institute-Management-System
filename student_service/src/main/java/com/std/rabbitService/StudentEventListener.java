package com.std.rabbitService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.std.event.StudentDroppedEvent;
import com.std.event.StudentEnrolledEvent;
import com.std.rabbitConfig.RabbitMQConfig;

@Service
public class StudentEventListener {

	@RabbitListener(queues = RabbitMQConfig.ENROLLED_QUEUE)
	public void handleEnrolled(StudentEnrolledEvent event) {
		System.out.println("🎓 Student Enrolled: ID=" + event.getStudentId() + ", Course=" + event.getCourseId());
	}

	@RabbitListener(queues = RabbitMQConfig.DROPPED_QUEUE)
	public void handleDropped(StudentDroppedEvent event) {
		System.out.println("📤 Student Dropped: ID=" + event.getStudentId() + ", Reason=" + event.getReason());
	}
}
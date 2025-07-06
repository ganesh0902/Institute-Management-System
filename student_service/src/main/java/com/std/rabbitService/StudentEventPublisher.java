package com.std.rabbitService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.std.event.StudentDroppedEvent;
import com.std.event.StudentEnrolledEvent;
import com.std.rabbitConfig.RabbitMQConfig;

@Service
public class StudentEventPublisher {

	 private final RabbitTemplate rabbitTemplate;
	 
	 
	 public StudentEventPublisher(RabbitTemplate rabbitTemplate) {
	        this.rabbitTemplate = rabbitTemplate;
	    }

	    public void publishEnrolled(StudentEnrolledEvent event) {
	        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ENROLED_ROUTING_KEY, event);
	    }

	    public void publishDropped(StudentDroppedEvent event) {
	        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.DROPED_ROUTING_KEY, event);
	    }
}

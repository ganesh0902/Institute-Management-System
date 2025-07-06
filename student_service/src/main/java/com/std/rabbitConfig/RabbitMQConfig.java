package com.std.rabbitConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String EXCHANGE = "Student.exchange";
	public static final String ENROLLED_QUEUE = "student.enrolled.queue";
	public static final String DROPPED_QUEUE = "student.dropped.queue";
	
	public static final String ENROLED_ROUTING_KEY = "student.controlled";
	public static final String DROPED_ROUTING_KEY = "student.dropped";
	
	@Bean
	public TopicExchange studentExchange() {
		return new TopicExchange(EXCHANGE);
	}
	
	@Bean
	public Queue enrolledQueue() {
		return new Queue(ENROLLED_QUEUE);
	}
	
	@Bean
	public Queue droppedQueue()
	{
		return new Queue(DROPPED_QUEUE);
	}
		
	@Bean
    public Binding enrolledBinding() {
        return BindingBuilder.bind(enrolledQueue()).to(studentExchange()).with(ENROLED_ROUTING_KEY);
    }
    
	@Bean
    public Binding dropedBinding()
    {
    	return BindingBuilder.bind(droppedQueue()).to(studentExchange()).with(DROPED_ROUTING_KEY);
    }
	
	@Bean
	public MessageConverter jsonMessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}

}
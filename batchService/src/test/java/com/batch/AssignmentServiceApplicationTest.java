package com.batch;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.batch.batchImpl.AssignmentImpl;
import com.batch.repository.AssignmentRepository;

@SpringBootTest
public class AssignmentServiceApplicationTest {

	@Mock
	private AssignmentRepository repositoty;

	@InjectMocks
	private AssignmentImpl serviceImpl;

	@Mock
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {

	}

	
}

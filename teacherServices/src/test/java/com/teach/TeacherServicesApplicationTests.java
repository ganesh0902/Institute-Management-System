package com.teach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.teach.entities.Teacher;
import com.teach.repository.TeacherRepository;
import com.teach.serviceimpl.TeacherServiceImpl;

@SpringBootTest
class TeacherServicesApplicationTests {

	@InjectMocks
	private TeacherServiceImpl serviceImpl;
	
	@Mock
	private TeacherRepository repository;
	
	@Mock
	private RestTemplate restTemplate;
	
	private Teacher teacherMock;
	
	private Teacher teacherExpect;
	
	
	@BeforeEach
	void contextLoads() {
				
		teacherMock = new Teacher();			
		teacherMock.setFirstName("Ganesh");
		teacherMock.setLastName("Sakhare");
		teacherMock.setContact("9595956150");
		teacherMock.setEducation("BCA");
		teacherMock.setImage("example.jpg");
		teacherMock.setCredentialId(12);
		teacherMock.setInstituteId(122l);
		teacherMock.setTId(12);
				
		teacherExpect = new Teacher();
		teacherExpect.setTId(12);
		teacherExpect.setFirstName("Ganesh");
		teacherExpect.setLastName("Sakhare");
		teacherExpect.setContact("9595956150");
		teacherExpect.setEducation("BCA");
		teacherExpect.setImage("example.jpg");
		teacherExpect.setCredentialId(12);
		teacherExpect.setInstituteId(122l);
				
	}
	@Test
	public void saveTeacherTest()
	{
		Mockito.when(this.repository.save(teacherMock)).thenReturn(teacherExpect);
		
		Teacher result = this.serviceImpl.saveTeacher(teacherMock);
		
		assertNotNull(result);
		assertEquals(12, result.getTId());
		assertEquals(teacherExpect, result);
		
		Mockito.verify(this.repository,times(1)).save(teacherMock);
	}
	
}

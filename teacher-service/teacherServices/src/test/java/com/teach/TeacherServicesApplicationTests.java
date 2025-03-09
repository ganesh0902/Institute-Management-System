package com.teach;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.Cache;
import com.teach.dto.TeacherDto;
import com.teach.dto.TeacherIdAndName;
import com.teach.entities.Teacher;
import com.teach.exception.ResourceNotFoundException;
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

	@Mock
	private CacheManager cacheManager;

	@Mock
	private Cache cache;

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
	public void saveTeacherTest() {
		Mockito.when(this.repository.save(teacherMock)).thenReturn(teacherExpect);
		Teacher result = this.serviceImpl.saveTeacher(teacherMock);

		assertNotNull(result);
		assertEquals(12, result.getTId());
		assertEquals(teacherExpect, result);

		Mockito.verify(this.repository, times(1)).save(teacherMock);
	}

	@Test
	public void saveTeacherTest_RuntimeException() {
		Teacher teacher = null;
		Mockito.when(this.repository.save(teacher)).thenThrow(new RuntimeException("Database Error"));

		RuntimeException result = assertThrows(RuntimeException.class, () -> {

			this.serviceImpl.saveTeacher(teacher);
		});

		assertEquals("Database Error", result.getMessage());
	}

	@Test
	public void updateTeacherTest() throws ResourceNotFoundException {
		int teacherId = 21;

		Mockito.when(this.repository.findById(teacherId)).thenReturn(Optional.of(teacherMock));
		Mockito.when(this.repository.save(teacherMock)).thenReturn(teacherExpect);

		// act
		Teacher result = this.serviceImpl.updateTeacher(teacherId, teacherMock);

		assertNotNull(result);
		assertEquals("Ganesh", result.getFirstName());

		verify(this.repository, times(1)).save(teacherMock);
	}

	@Test
	public void updateTeacherTest_runtimeException() throws ResourceNotFoundException {
		int teacherId = 0;

		Mockito.when(this.repository.findById(teacherId)).thenThrow(new RuntimeException("Database Error"));

		RuntimeException result = assertThrows(RuntimeException.class, () -> {
			this.serviceImpl.updateTeacher(teacherId, teacherExpect);
		});

		assertEquals("Database Error", result.getMessage());

	}

	@Test
	public void testUpdateTeacher_EmptyFields_ShouldNotUpdateFields() throws ResourceNotFoundException {
		// Arrange
		int validId = 1;
		Teacher existingTeacher = new Teacher();
		existingTeacher.setFirstName("John");
		existingTeacher.setLastName("Doe");

		Teacher teacherDto = new Teacher(); // All fields are empty strings
		teacherDto.setFirstName("");
		teacherDto.setLastName("");

		Mockito.when(this.repository.findById(validId)).thenReturn(Optional.of(existingTeacher));
		Mockito.when(this.repository.save(any(Teacher.class))).thenReturn(existingTeacher);

		// Act
		Teacher updatedTeacher = serviceImpl.updateTeacher(validId, teacherDto);

		// Assert
		assertEquals("John", updatedTeacher.getFirstName());
		assertEquals("Doe", updatedTeacher.getLastName());
	}

	@Test
	public void testUpdateTeacher_DatabaseFailure_ShouldThrowException() {
		int teacherId = 12;

		Mockito.when(this.repository.findById(teacherId)).thenReturn(Optional.of(teacherExpect));
		Mockito.when(repository.save(any(Teacher.class))).thenThrow(new RuntimeException("Database Error"));

		RuntimeException result = assertThrows(RuntimeException.class, () -> {

			this.serviceImpl.updateTeacher(teacherId, teacherExpect);
		});

		assertEquals("Database Error", result.getMessage());
	}

	@Test
	public void getAllTest() {
		long instituteId = 21;
		List<Teacher> singletonList = Collections.singletonList(teacherExpect);

		Mockito.when(this.repository.findAllByInstitute(instituteId)).thenReturn(singletonList);

		List<TeacherDto> result = this.serviceImpl.getAll(instituteId);

		assertNotNull(result);
		assertEquals(12, result.get(0).getTId());
	}

	@Test
	public void getAllTest_runtimException() {
		long instituteId = 21;
		List<Teacher> singletonList = Collections.singletonList(teacherExpect);

		Mockito.when(this.repository.findAllByInstitute(instituteId)).thenThrow(new RuntimeException("Database Error"));

		RuntimeException result = assertThrows(RuntimeException.class, () -> {
			this.serviceImpl.getAll(instituteId);
		});

		assertNotNull(result);
		assertEquals("Database Error", result.getMessage());

		verify(this.repository, times(1)).findAllByInstitute(instituteId);
	}

	@Test
	public void deleteTest() throws ResourceNotFoundException {
		int teacherId = 12;

		Mockito.when(this.repository.findById(teacherId)).thenReturn(Optional.of(teacherExpect));

		boolean delete = this.serviceImpl.delete(teacherId);

		assertEquals(true, delete);
	}

	@Test
	public void testDelete_TeacherNotFound_ShouldThrowResourceNotFoundException() throws ResourceNotFoundException {
		int teacherId = 12;

		Mockito.when(repository.findById(teacherId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {

			this.serviceImpl.delete(teacherId);
		});
	}

	@Test
	public void testDelete_DatabaseFailure_ShouldThrowException() {
		int teacherId = 12;
		Mockito.when(this.repository.findById(teacherId)).thenThrow(new RuntimeException("Database Error"));

		RuntimeException result = assertThrows(RuntimeException.class, () -> {

			this.serviceImpl.delete(teacherId);
		});

		assertEquals("Database Error", result.getMessage());

		verify(this.repository, times(1)).findById(teacherId);
	}

	@Test
	public void testDelete_TeacherAlreadyDeleted_ShouldThrowResourceNotFoundException() {

		int validId = 1;
		Mockito.when(repository.findById(validId)).thenReturn(Optional.empty()); // Already deleted

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			serviceImpl.delete(validId);
		});
	}

	@Test
	public void testGetTeacherIdAndName() {
		List<Object[]> mockResult = Arrays.asList(new Object[] { 1, "John Doe" }, new Object[] { 2, "Jane Smith" });

		long instituteId = 21;

		Mockito.when(this.repository.getTeacherIdAndName(instituteId)).thenReturn(mockResult);

		List<TeacherIdAndName> result = this.serviceImpl.getTeacherIdAndName(instituteId);

		assertNotNull(result);
		assertEquals(1, result.get(0).getTeacherId());
		assertEquals("John Doe", result.get(0).getTeacherName());
		verify(this.repository, times(1)).getTeacherIdAndName(instituteId);
	}

	@Test
	public void testGetTeacherIdAndName_NoTeachers_ShouldReturnEmptyList() {
		// Arrange
		long instituteId = 999L; // No teachers for this institute
		Mockito.when(repository.getTeacherIdAndName(instituteId)).thenReturn(Collections.emptyList());

		// Act
		List<TeacherIdAndName> result = serviceImpl.getTeacherIdAndName(instituteId);

		// Assert
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetTeacherIdAndName_DatabaseFailure_ShouldThrowException() {
		// Arrange
		long instituteId = 1L;
		Mockito.when(repository.getTeacherIdAndName(instituteId)).thenThrow(new DataAccessException("Database error") {
		});

		// Act & Assert
		assertThrows(DataAccessException.class, () -> {
			serviceImpl.getTeacherIdAndName(instituteId);
		});
	}

	@Test
	public void testGetTeacherIdAndName_InvalidInstituteId_ShouldReturnEmptyList() {
		// Arrange
		long invalidInstituteId = 0L;
		Mockito.when(repository.getTeacherIdAndName(invalidInstituteId)).thenReturn(Collections.emptyList());

		// Act
		List<TeacherIdAndName> result = serviceImpl.getTeacherIdAndName(invalidInstituteId);

		// Assert
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetTeacherCount_ValidInstituteId_ShouldReturnCount() {
		// Arrange
		Long instituteId = 1L;
		long expectedCount = 10L;
		Mockito.when(repository.TeacherCount(instituteId)).thenReturn(expectedCount);

		// Act
		long result = serviceImpl.getTeacherCount(instituteId);

		// Assert
		assertEquals(expectedCount, result);
		verify(repository, times(1)).TeacherCount(instituteId);
	}
	@Test
	public void testGetTeacherCount_NoTeachers_ShouldReturnZero() {
	    // Arrange
	    Long instituteId = 2L;
	    Mockito.when(repository.TeacherCount(instituteId)).thenReturn(0L);

	    // Act
	    long result = serviceImpl.getTeacherCount(instituteId);

	    // Assert
	    assertEquals(0L, result);
	    verify(repository, times(1)).TeacherCount(instituteId);
	}
	@Test
	public void testGetTeacherCount_DatabaseFailure_ShouldThrowException() {
	    // Arrange
	    Long instituteId = 1L;
	    Mockito.when(repository.TeacherCount(instituteId)).thenThrow(new DataAccessException("Database error") {});

	    // Act & Assert
	    assertThrows(DataAccessException.class, () -> {
	        serviceImpl.getTeacherCount(instituteId);
	    });
	}

}
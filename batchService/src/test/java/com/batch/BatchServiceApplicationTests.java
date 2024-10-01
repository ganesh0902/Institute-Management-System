package com.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.config.ConfigData.Options;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Optionals;
import org.springframework.web.client.RestTemplate;

import com.batch.batchImpl.BatchServiceImpl;
import com.batch.dto.BatchDto;
import com.batch.dto.BatchTitleAndDate;
import com.batch.dto.TeacherDto;
import com.batch.entities.Batch;
import com.batch.entities.Course;
import com.batch.exception.ResourceNotFoundException;
import com.batch.repository.BatchRepository;
import com.batch.service.batchService;
import com.jayway.jsonpath.Option;

@SpringBootTest
class BatchServiceApplicationTests {

	@Mock
	private BatchRepository repository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private BatchServiceImpl serviceImpl;

	private Batch existingBatch;
	private Batch updateBatch;

	@Test
	void contextLoads() {

	}

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);

		// Set up existing batch
		existingBatch = new Batch();
		existingBatch.setBatchTitle("Original Title");
		existingBatch.setDuration("6 months");
		existingBatch.setStartDate("2024-01-01");
		existingBatch.setEndDate("2024-06-30");
		existingBatch.setStatus("Active");
		existingBatch.setTeacherId(1);
		existingBatch.setTime("10:00 AM");

		// Set up update batch
		updateBatch = new Batch();
		updateBatch.setBatchTitle("Updated Title");
		updateBatch.setDuration("3 months");
		updateBatch.setStartDate("2024-02-01");
		updateBatch.setEndDate("2024-05-30");
		updateBatch.setStatus("Inactive");
		updateBatch.setTeacherId(2);
		updateBatch.setTime("02:00 PM");

	}

	@Test
	public void testGetexistBatchSuccess() throws ResourceNotFoundException {

		int bId = 1;

		Batch batch = new Batch();
		batch.setBId(bId);
		batch.setBatchTitle("Java Batch");
		batch.setDuration("6 Months");
		batch.setStartDate("");
		batch.setEndDate("");
		batch.setStatus("");
		batch.setLocation("");
		batch.setTime("");
		batch.setImage("");
		batch.setTeacherId(10);
		batch.setCourseId(21);
		batch.setInstituteId(21l);

		Mockito.when(repository.findById(bId)).thenReturn(Optional.of(batch));

		Course mockCourse = new Course();
		mockCourse.setCid(10);
		mockCourse.setCourseName("Java");
		mockCourse.setDescription("This course is for Java Developer");
		mockCourse.setFees("2022");
		mockCourse.setSkills("Java, Python");

		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setTId(21);
		teacherDto.setFirstName("Ganesh");
		teacherDto.setLastName("Sakhare");
		teacherDto.setEducation("BCA");
		teacherDto.setContact("9595956150");
		teacherDto.setEmail("ganeshs2987@gmail.com");
		teacherDto.setEmail("aaa.jpg");

		// Mock The restTemplate
		Mockito.when(restTemplate.getForObject("http://course-service/course/" + batch.getCourseId(), Course.class))
				.thenReturn(mockCourse);

		Mockito.when(
				restTemplate.getForObject("http://teacher-service/teacher/" + batch.getTeacherId(), TeacherDto.class))
				.thenReturn(teacherDto);

		// act
		BatchDto batchResult = serviceImpl.getBatch(bId);

		assertNotNull(batchResult);
		assertEquals("Java Batch", batchResult.getBatchTitle());
		assertEquals("Java", batchResult.getCourse().getCourseName());
		assertEquals("Ganesh", batchResult.getTeacherDto().getFirstName());

		Mockito.verify(repository, times(1)).findById(bId);
		Mockito.verify(restTemplate, times(1)).getForObject("http://course-service/course/" + batch.getCourseId(),
				Course.class);
		Mockito.verify(restTemplate, times(1)).getForObject("http://teacher-service/teacher/" + batch.getTeacherId(),
				TeacherDto.class);
	}

	@Test()
	public void testBatchNotFound() {
		int bId = 21;

		when(repository.findById(bId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> serviceImpl.getBatch(bId));
	}

	@Test
	public void saveBatch() {
		Batch batch = new Batch();

		batch.setBId(10);
		batch.setBatchTitle("Java Development");
		batch.setCourseId(10);
		batch.setDuration("4 Months");
		batch.setStartDate("09-01-2009");
		batch.setEndDate("09-08-2009");
		batch.setImage("");
		batch.setStatus("pending");
		batch.setTeacherId(101);
		batch.setTime("09:20");

		Mockito.when(repository.save(batch)).thenReturn(batch);

		Batch result = this.serviceImpl.saveBatch(batch);

		assertNotNull(result);
		assertEquals(batch, result);

		verify(repository, times(1)).save(batch);
	}

	@Test
	public void updateBatch() throws ResourceNotFoundException {
		int batchId = 21;

		Mockito.when(this.repository.findById(batchId)).thenReturn(Optional.of(updateBatch));
		Mockito.when(this.repository.save(updateBatch)).thenReturn(updateBatch);

		Batch updatedBatch = this.serviceImpl.updateBatch(batchId, updateBatch);

		// Verify that the fields are updated correctly
		assertEquals("Updated Title", updatedBatch.getBatchTitle());
		assertEquals("3 months", updatedBatch.getDuration());
		assertEquals("2024-02-01", updatedBatch.getStartDate());
		assertEquals("2024-05-30", updatedBatch.getEndDate());
		assertEquals("Inactive", updatedBatch.getStatus());
		assertEquals(2, updatedBatch.getTeacherId());
		assertEquals("02:00 PM", updatedBatch.getTime());

		// Verify that save was called
		verify(repository, times(1)).save(updatedBatch);
	}

	@Test
	public void testUpdateBatch_ResourceNotFoundException() {
		int batchId = 21;

		Mockito.when(this.repository.findById(batchId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			serviceImpl.updateBatch(batchId, updateBatch);
		});

		verify(repository, never()).save(any(Batch.class));
	}

	@Test
	public void testDeleteBatch() throws ResourceNotFoundException {
		int bId = 21;

		Mockito.when(this.repository.findById(bId)).thenReturn(Optional.of(this.existingBatch));

		boolean result = serviceImpl.delete(bId);

		assertEquals(true, result);

		verify(repository, times(1)).findById(bId);
		verify(repository, times(1)).deleteById(bId);
	}

	@Test
	public void testDeleteBatch_ResourceNotFoundException() {
		int batchId = 21;

		Mockito.when(this.repository.findById(batchId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {

			this.serviceImpl.delete(batchId);
		});

		verify(repository, times(1)).findById(batchId);
	}

	@Test
	public void testGetAllBatch() {
		int instituteId = 1;

		Batch batch = new Batch();
		batch.setBId(21);
		batch.setBatchTitle("Java Development");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(22);
		batch.setCourseId(21);
		batch.setInstituteId(21l);

		List<Batch> singletonList = Collections.singletonList(batch);

		Course course = new Course();
		course.setCid(1);
		course.setCourseName("Java Development");
		course.setDescription("This course is for Java Developer");
		course.setFees("1222");
		course.setSkills("Java, Html, Spring boot");

		TeacherDto teacher = new TeacherDto();
		teacher.setTId(22);
		teacher.setFirstName("Ganesh");
		teacher.setLastName("Sakhare");
		teacher.setEducation("BCA");
		teacher.setContact("292929292");
		teacher.setEmail("exapl123@gmail.com");
		teacher.setImage("exampl.jpg");

		Mockito.when(this.repository.findAllBatchByInstituteId(instituteId)).thenReturn(singletonList);

		Mockito.when(restTemplate.getForObject("http://course-service/course/21", Course.class)).thenReturn(course);

		Mockito.when(restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenReturn(teacher);

		List<BatchDto> result = this.serviceImpl.getAllBatch(instituteId);

		assertNotNull(result);
		assertEquals(course, result.get(0).getCourse());
		assertEquals(teacher, result.get(0).getTeacherDto());

		verify(repository, times(1)).findAllBatchByInstituteId(instituteId);

		verify(restTemplate, times(1)).getForObject("http://course-service/course/21", Course.class);

		verify(restTemplate, times(1)).getForObject("http://teacher-service/teacher/22", TeacherDto.class);
	}

	@Test
	public void testGetAllBatchResourceNotFoundException() {
		int instituteId = 1;

		Mockito.when(this.repository.findAllBatchByInstituteId(instituteId)).thenReturn(Collections.EMPTY_LIST);

		List<BatchDto> result = this.serviceImpl.getAllBatch(instituteId);

		assertNotNull(result);
		assertTrue(result.isEmpty());

		verify(repository, times(1)).findAllBatchByInstituteId(instituteId);
	}

	@Test
	public void testGetAllBatch_NullCourse() {
		int instituteId = 1;

		Batch batch = new Batch();
		batch.setBId(21);
		batch.setBatchTitle("Java Development");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(22);
		batch.setCourseId(21);
		batch.setInstituteId(21l);

		List<Batch> singletonList = Collections.singletonList(batch);

		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setTId(21);
		teacherDto.setEducation("BCA");
		teacherDto.setEmail("ganesh123@gmail.com");
		teacherDto.setFirstName("Ganesh");
		teacherDto.setLastName("Sakhare");
		teacherDto.setContact("23323433334");
		teacherDto.setImage("");

		Mockito.when(this.repository.findAllBatchByInstituteId(instituteId)).thenReturn(singletonList);

		Mockito.when(this.restTemplate.getForObject("http://course-service/course/21", Course.class)).thenReturn(null);

		Mockito.when(this.restTemplate.getForObject("http://teacher-service/teacher/21", TeacherDto.class))
				.thenReturn(teacherDto);

		List<BatchDto> result = this.serviceImpl.getAllBatch(instituteId);

		assertNotNull(result);
		assertNull(result.get(0).getCourse());
	}

	@Test
	public void testGetAllBatch_NullTeacher() {
		int instituteId = 21;

		Batch batch = new Batch();
		batch.setBId(21);
		batch.setBatchTitle("java Development");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(21);
		batch.setCourseId(23);
		batch.setInstituteId(23l);

		List<Batch> singletonList = Collections.singletonList(batch);

		Course course = new Course();
		course.setCid(21);
		course.setCourseName("Java Development");
		course.setDescription("This course is for java development");
		course.setFees("2000");
		course.setSkills("Java, Python");

		Mockito.when(this.repository.findAllBatchByInstituteId(instituteId)).thenReturn(singletonList);

		Mockito.when(this.restTemplate.getForObject("http://course-service/course/23", Course.class))
				.thenReturn(course);

		Mockito.when(this.restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenReturn(null);

		List<BatchDto> result = this.serviceImpl.getAllBatch(instituteId);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertNull(result.get(0).getTeacherDto());
		assertEquals(course, result.get(0).getCourse());

		verify(repository, times(1)).findAllBatchByInstituteId(instituteId);
		verify(restTemplate, times(1)).getForObject("http://course-service/course/23", Course.class);
		// verify(restTemplate,
		// times(1)).getForObject("http://teacher-service/teacher/22",TeacherDto.class);
	}

	@Test
	public void testGetAllBatch_CourseServiceException() {
		int instituteId = 21;

		Batch batch = new Batch();
		batch.setBId(21);
		batch.setBatchTitle("java Development");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(21);
		batch.setCourseId(23);
		batch.setInstituteId(23l);

		List<Batch> singletonList = Collections.singletonList(batch);

		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setTId(24);

		Mockito.when(this.repository.findAllBatchByInstituteId(instituteId)).thenReturn(singletonList);

		Mockito.when(this.restTemplate.getForObject("http://course-service/course/23", Course.class))
				.thenThrow(new RuntimeException("Course Service not available"));

		Mockito.when(this.restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenReturn(teacherDto);

		List<BatchDto> result = null;

		try {
			result = this.serviceImpl.getAllBatch(instituteId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		assertNull(result);
	}

	@Test
	public void testGetAllBatch_TeacherServiceException() {
		int instituteId = 25;

		Batch batch = new Batch();
		batch.setBId(26);
		batch.setBatchTitle("Java Development");
		batch.setDuration("6 Months");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(21);
		batch.setCourseId(23);
		batch.setInstituteId(23l);

		List<Batch> singletonList = Collections.singletonList(batch);

		Mockito.when(this.repository.findAllBatchByInstituteId(instituteId)).thenReturn(singletonList);

		Course course = new Course();
		course.setCid(23);

		Mockito.when(this.restTemplate.getForObject("http://course-service/course/23", Course.class))
				.thenReturn(course);

		Mockito.when(this.restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenThrow(new RuntimeException("Teacher service is not available"));

		List<BatchDto> result = null;

		try {
			result = this.serviceImpl.getAllBatch(instituteId);
		} catch (Exception e) {
		}

		assertNotNull(result);
		assertEquals(course, result.get(0).getCourse());
		assertNull(result.get(0).getTeacherDto());
	}

	@Test
	public void getAllBatch_RepositoryException() {
		int instituteId = 21;

		Mockito.when(this.repository.findAllBatchByInstituteId(instituteId))
				.thenThrow(new RuntimeException("Database Error"));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {

			serviceImpl.getAllBatch(instituteId);
		});

		assertEquals("Database Error", exception.getMessage());
	}

	@Test
	public void testGetAllBatchesByTeacherId() {
		Batch batch = new Batch();
		batch.setBId(26);
		batch.setBatchTitle("Java Development");
		batch.setDuration("6 Months");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(22);
		batch.setCourseId(21);
		batch.setInstituteId(23l);

		List<Batch> singletonList = Collections.singletonList(batch);
		int tId = 21;

		Course course = new Course();
		course.setCid(21);
		course.setCourseName("Java Development");
		course.setDescription("This course is for Java Development");
		course.setFees("2000");
		course.setSkills("Java, Spring boot, Python");

		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setTId(22);
		teacherDto.setFirstName("Ganesh");
		teacherDto.setLastName("Sakhare");
		teacherDto.setEducation("BCA");
		teacherDto.setContact("9595956150");
		teacherDto.setEmail("exmple123@gmail.com");
		teacherDto.setImage("");

		Mockito.when(this.repository.findAllByTeacherId(tId)).thenReturn(singletonList);

		Mockito.when(this.restTemplate.getForObject("http://course-service/course/21", Course.class))
				.thenReturn(course);
		Mockito.when(this.restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenReturn(teacherDto);

		List<BatchDto> result = this.serviceImpl.getAllBatchByTeacherId(tId);

		assertNotNull(result);
		assertEquals(course, result.get(0).getCourse());
		assertEquals(teacherDto, result.get(0).getTeacherDto());

		verify(this.repository, times(1)).findAllByTeacherId(tId);
		verify(this.restTemplate, times(1)).getForObject("http://course-service/course/21", Course.class);
		verify(this.restTemplate, times(1)).getForObject("http://teacher-service/teacher/22", TeacherDto.class);
	}

	@SuppressWarnings("null")
	@Test
	public void testGetAllBatchesByTeacherId_TeacherServiceException() {

		int teacherId = 21;
		Batch batch = new Batch();
		batch.setBId(26);
		batch.setBatchTitle("Java Development");
		batch.setDuration("6 Months");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(22);
		batch.setCourseId(21);
		batch.setInstituteId(23l);

		List<Batch> singletonList = Collections.singletonList(batch);

		Course course = new Course();
		course.setCid(21);
		course.setCourseName("Java Development");
		course.setDescription("This course is for Java Development");
		course.setFees("2000");
		course.setSkills("Java, Python, Sql");

		Mockito.when(this.repository.findAllByTeacherId(teacherId)).thenReturn(singletonList);

		Mockito.when(this.restTemplate.getForObject("http://course-service/course/21", Course.class))
				.thenReturn(course);

		Mockito.when(this.restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenThrow(new RuntimeException("Teacher Service is not available"));

		List<BatchDto> result = null;
		try {
			result = this.serviceImpl.getAllBatchByTeacherId(teacherId);
		} catch (Exception e) {
		}

		assertNull(result);
	}

	@Test
	public void testGetAllBatchesByTeacherId_CourseServiceException() {

		int teacherId = 21;
		Batch batch = new Batch();
		batch.setBId(26);
		batch.setBatchTitle("Java Development");
		batch.setDuration("6 Months");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(22);
		batch.setCourseId(23);
		batch.setInstituteId(23l);

		List<Batch> singletonList = Collections.singletonList(batch);

		Mockito.when(this.repository.findAllByTeacherId(teacherId)).thenReturn(singletonList);

		Mockito.when(this.restTemplate.getForObject("http://course-service/course/23", Course.class))
				.thenThrow(new RuntimeException("Course Service is not Available"));

		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setTId(22);
		teacherDto.setFirstName("Ganesh");
		teacherDto.setLastName("Sakhare");
		teacherDto.setContact("9595956150");
		teacherDto.setEducation("BCA");
		teacherDto.setImage("J");
		teacherDto.setEmail("exxample123@gmail.com");

		Mockito.when(this.restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenReturn(teacherDto);

		List<BatchDto> result = null;
		try {
			result = this.serviceImpl.getAllBatchByTeacherId(teacherId);
		} catch (Exception e) {
		}

		assertNull(result);
	}

	public void testGetAllBatchesByTeacherId_RepositoryException() {
		int teacherId = 20;

		Mockito.when(this.repository.findAllByTeacherId(teacherId)).thenThrow(new RuntimeException("Database Error"));

		Exception result = assertThrows(RuntimeException.class, () -> {

			this.serviceImpl.getAllBatchByTeacherId(teacherId);
		});

		assertEquals("Database Error", result.getMessage());
	}

	@Test
	public void testFindByBatchTitleContaining() {
		String batchTitle = "Java";
		Batch batch = new Batch();
		batch.setBId(26);
		batch.setBatchTitle("Java Development");
		batch.setDuration("6 Months");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(22);
		batch.setCourseId(23);
		batch.setInstituteId(23l);

		List<Batch> singletonList = Collections.singletonList(batch);

		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setTId(22);
		teacherDto.setFirstName("Ganesh");
		teacherDto.setLastName("Sakhare");
		teacherDto.setEducation("BCA");
		teacherDto.setContact("9595956150");
		teacherDto.setImage("");
		teacherDto.setEmail("ganesh1234@gmail.com");

		Course course = new Course();
		course.setCid(23);
		course.setCourseName("Java Development");
		course.setDescription("This course is for Java Development");
		course.setFees("2000");
		course.setSkills("Java, Spring boot");

		Mockito.when(this.repository.findByBatchTitleContaining(batchTitle)).thenReturn(singletonList);

		Mockito.when(this.restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenReturn(teacherDto);

		Mockito.when(this.restTemplate.getForObject("http://course-service/course/23", Course.class))
				.thenReturn(course);

		List<BatchDto> result = this.serviceImpl.findByBatchTitleContaining(batchTitle);

		assertNotNull(result);
		assertEquals(course, result.get(0).getCourse());
		assertEquals(teacherDto, result.get(0).getTeacherDto());

		verify(this.repository, times(1)).findByBatchTitleContaining(batchTitle);
		verify(this.restTemplate, times(1)).getForObject("http://teacher-service/teacher/22", TeacherDto.class);
		verify(this.restTemplate, times(1)).getForObject("http://course-service/course/23", Course.class);

	}

	@Test
	public void testFindByBatchTitleContaining_TeacherServiceException() {
		String batchTitle = "Java";
		Batch batch = new Batch();
		batch.setBId(26);
		batch.setBatchTitle("Java Development");
		batch.setDuration("6 Months");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(22);
		batch.setCourseId(24);
		batch.setInstituteId(23l);

		List<Batch> singletonList = Collections.singletonList(batch);

		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setTId(22);
		teacherDto.setFirstName("Ganesh");
		teacherDto.setLastName("Sakhare");
		teacherDto.setEducation("BCA");
		teacherDto.setContact("9595956150");
		teacherDto.setImage("");
		teacherDto.setEmail("ganesh1234@gmail.com");

		Course course = new Course();
		course.setCid(23);
		course.setCourseName("Java Development");
		course.setDescription("This course is for Java Development");
		course.setFees("2000");
		course.setSkills("Java, Spring boot");

		Mockito.when(this.repository.findByBatchTitleContaining(batchTitle)).thenReturn(singletonList);

		Mockito.when(restTemplate.getForObject("http://course-service/course/23", Course.class)).thenReturn(course);

		Mockito.when(restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenThrow(new RuntimeException("Teacher Service is not available"));

		List<BatchDto> result = null;
		try {
			result = this.serviceImpl.findByBatchTitleContaining(batchTitle);
		} catch (Exception e) {
		}

		assertNull(result);

		verify(repository, times(1)).findByBatchTitleContaining(batchTitle);
		verify(restTemplate, times(1)).getForObject("http://course-service/course/24", Course.class);
		verify(restTemplate, times(1)).getForObject("http://teacher-service/teacher/22", TeacherDto.class);

	}

	@Test
	public void testFindByBatchTitleContaining_CourseServiceException() {
		String batchTitle = "Java";
		Batch batch = new Batch();
		batch.setBId(26);
		batch.setBatchTitle("Java Development");
		batch.setDuration("6 Months");
		batch.setDuration("2 Months");
		batch.setStartDate("09-02-2001");
		batch.setEndDate("09-05-2001");
		batch.setImage("");
		batch.setLocation("Pune");
		batch.setTeacherId(22);
		batch.setCourseId(23);
		batch.setInstituteId(23l);

		List<Batch> singletonList = Collections.singletonList(batch);

		TeacherDto teacherDto = new TeacherDto();
		teacherDto.setTId(22);
		teacherDto.setFirstName("Ganesh");
		teacherDto.setLastName("Sakhare");
		teacherDto.setEducation("BCA");
		teacherDto.setContact("9595956150");
		teacherDto.setImage("");
		teacherDto.setEmail("ganesh1234@gmail.com");

		Course course = new Course();
		course.setCid(23);
		course.setCourseName("Java Development");
		course.setDescription("This course is for Java Development");
		course.setFees("2000");
		course.setSkills("Java, Spring boot");

		Mockito.when(this.repository.findByBatchTitleContaining(batchTitle)).thenReturn(singletonList);

		Mockito.when(this.restTemplate.getForObject("http://course-service/course/23", Course.class))
				.thenThrow(new RuntimeException("Course Service is not available"));

		Mockito.when(this.restTemplate.getForObject("http://teacher-service/teacher/22", TeacherDto.class))
				.thenReturn(teacherDto);

		List<BatchDto> result = null;
		try {
			result = this.serviceImpl.findByBatchTitleContaining(batchTitle);
		} catch (Exception e) {
		}

		assertNull(result);

		verify(this.repository, times(1)).findByBatchTitleContaining(batchTitle);
		verify(this.restTemplate, times(1)).getForObject("http://course-service/course/23", Course.class);
	}

	@Test
	public void testFindByBatchTitleContaining_RepositoryException() {
		String batchTitle = "Java";
		Mockito.when(repository.findByBatchTitleContaining(batchTitle))
				.thenThrow(new RuntimeException("Database Error"));

		Exception result = assertThrows(RuntimeException.class, () -> {
			serviceImpl.findByBatchTitleContaining(batchTitle);
		});

		assertEquals("Database Error", result.getMessage());
	}

	@Test
	public void testGetBatchTitleAndDate() {
		int instituteId = 23;
		BatchTitleAndDate batchTitleAndDate = new BatchTitleAndDate();
		batchTitleAndDate.setBatchTitle("Java Development");
		batchTitleAndDate.setBId(21);
		batchTitleAndDate.setStartDate("2024-05-05");

		List<BatchTitleAndDate> singletonList = Collections.singletonList(batchTitleAndDate);

		Mockito.when(this.repository.getBatchTitleAndStartDate(instituteId)).thenReturn(singletonList);

		List<BatchTitleAndDate> result = this.serviceImpl.getBatchTitleAndDate(instituteId);

		assertNotNull(result);
		assertEquals("Java Development", result.get(0).getBatchTitle());
		assertEquals("2024-05-05", result.get(0).getStartDate());
		assertEquals(singletonList, result);

		verify(this.repository, times(1)).getBatchTitleAndStartDate(instituteId);
	}

	@Test
	public void testGetBatchTitleAndDateEmpty() {
		int instituteId = 23;

		List<BatchTitleAndDate> singletonList = new ArrayList<>();

		Mockito.when(this.repository.getBatchTitleAndStartDate(instituteId)).thenReturn(singletonList);

		List<BatchTitleAndDate> result = this.serviceImpl.getBatchTitleAndDate(instituteId);

		assertNotNull(result);
		assertEquals(singletonList, result);

		verify(this.repository, times(1)).getBatchTitleAndStartDate(instituteId);
	}

	@Test
	public void testGetSingleBatch() throws ResourceNotFoundException {
		int batchId = 21;

		Batch batch = new Batch();
		batch.setBId(21);
		batch.setBatchTitle("Java Development");
		batch.setCourseId(12);
		batch.setDuration("6 Months");
		batch.setStartDate("2024-05-05");
		batch.setEndDate("2024-05-05");
		batch.setImage("hhh");
		batch.setInstituteId(21l);
		batch.setTeacherId(25);
		batch.setTime("09:30");
		batch.setLocation("Pune");

		Mockito.when(this.repository.findById(batchId)).thenReturn(Optional.of(batch));

		Batch result = this.serviceImpl.getSingleBatch(batchId);

		assertNotNull(result);
		assertEquals(batch, result);

		verify(this.repository, times(1)).findById(batchId);
	}

	@Test
	public void testGetSingleBatch_resourceNotFoundException() {
		int batchId = 21;

		Mockito.when(this.repository.findById(batchId)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {

			this.serviceImpl.getSingleBatch(batchId);
		});

		assertEquals("Batch is not found with Id : 21", exception.getMessage());
		verify(repository, times(1)).findById(batchId);
	}

	@Test
	public void testCountBatchAvailable() {
		int instituteId = 23;
		Long count = 2l;
		Mockito.when(this.repository.countBatchAvailable(instituteId)).thenReturn(count);

		Long result = this.serviceImpl.countBatchAvailable(instituteId);

		assertEquals(2, result);
		verify(this.repository, times(1)).countBatchAvailable(instituteId);
	}

	public void testCountBatchAvailable_Empty() {
		int instituteId = 23;
		Long count = 0l;

		Mockito.when(this.repository.countBatchAvailable(instituteId)).thenReturn(0l);

		Long result = this.serviceImpl.countBatchAvailable(instituteId);

		assertNotNull(result);
		assertEquals(count, result);

		verify(this.repository, times(1)).countBatchAvailable(instituteId);
	}

	@Test
	public void testGetBatchesByTeacherId() {
		int teacherId = 23;

		Batch batch = new Batch();
		batch.setBId(24);
		batch.setBatchTitle("Java Development");
		batch.setDuration("6 Months");
		batch.setStartDate("2024-05-05");
		batch.setEndDate("2024-08-05");
		batch.setImage("");
		batch.setInstituteId(2l);
		batch.setStatus("pending");
		batch.setTime("09:30");
		batch.setLocation("Pune");
		batch.setCourseId(22);
		batch.setTeacherId(23);

		List<Batch> singletonList = Collections.singletonList(batch);

		Mockito.when(this.repository.findAllByTeacherId(teacherId)).thenReturn(singletonList);

		List<BatchDto> result = this.serviceImpl.getAllBatchByTeacherId(teacherId);

		assertNotNull(result);
		assertEquals("Java Development", result.get(0).getBatchTitle());

		verify(this.repository, times(1)).findAllByTeacherId(teacherId);
	}

	@Test
	public void testGetBatchesByTeacherId_NotFound() {
		int teacherId = 23;

		Mockito.when(this.repository.findAllByTeacherId(teacherId)).thenReturn(Collections.EMPTY_LIST);

		List<Batch> result = this.serviceImpl.getBatchesByTeacherId(teacherId);

		assertTrue(result.isEmpty());

		verify(this.repository, times(1)).findAllByTeacherId(teacherId);
	}

	@Test
	public void testGetBatchesByTeacherId_ThrowsException() {
		int teacherId = 23;

		Mockito.when(this.repository.findAllByTeacherId(teacherId)).thenThrow(new RuntimeException("Database Error"));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {

			this.serviceImpl.getAllBatchByTeacherId(teacherId);
		});

		assertEquals("Database Error", exception.getMessage());

	}
}

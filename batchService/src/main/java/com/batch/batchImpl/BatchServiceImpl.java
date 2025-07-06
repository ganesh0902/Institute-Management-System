package com.batch.batchImpl;

import java.util.ArrayList;


import java.util.List;

import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.batch.common.GetTokenFromHeader;
import com.batch.dto.BatchDto;
import com.batch.dto.BatchTitleAndDate;
import com.batch.dto.StudentDto;
import com.batch.dto.TeacherDto;
import com.batch.entities.Batch;
import com.batch.entities.Course;
import com.batch.exception.ResourceNotFoundException;
import com.batch.exception.ServiceFailureException;
import com.batch.fallback.BatchFallBack;
import com.batch.repository.BatchRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpMethod;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class BatchServiceImpl implements com.batch.service.batchService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BatchRepository repository;

	@Autowired
	private BatchFallBack fallBack;
	
	@Override
	@CircuitBreaker(name = "getBatchFallBack", fallbackMethod = "getBatchFallBack")
	public BatchDto getBatch(int bId) throws ResourceNotFoundException {

		BatchDto batchDto = new BatchDto();
		Batch batch = this.repository.findById(bId)
				.orElseThrow(() -> new ResourceNotFoundException("Batch", " Id ", String.valueOf(bId)));

		batchDto.setBatchTitle(batch.getBatchTitle());
		batchDto.setStartDate(batch.getStartDate());
		batchDto.setEndDate(batch.getEndDate());
		batchDto.setDuration(batch.getDuration());
		batchDto.setImage(batch.getImage());
		batchDto.setStatus(batch.getStatus());
		batchDto.setTime(batch.getTime());
		batchDto.setBId(batch.getBId());
		batchDto.setLocation(batch.getLocation());
		batchDto.setCourseId(batch.getCourseId());
		batchDto.setTeacherId(batch.getTeacherId());

		try {
			Course course = this.restTemplate.getForObject("http://course-service/course/" + batch.getCourseId(),
					Course.class);
			batchDto.setCourse(course);
		} catch (Exception e) {
			throw new ServiceFailureException("Course Service is failed");

		}

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", getToken());
			HttpEntity<String> entity = new HttpEntity<>(headers);									
			
			ResponseEntity<TeacherDto> teacherResponse = restTemplate.exchange(
				    "http://teacher-service/teacher/" + batch.getTeacherId(),
				    HttpMethod.GET,
				    entity,
				    TeacherDto.class
				);
				batchDto.setTeacherDto(teacherResponse.getBody());			
			
		} catch (Exception e) {
			throw new ServiceFailureException("Teacher Service is failed");
		}

		return batchDto;
	}

	public BatchDto getBatchFallBack(int bId, Throwable throwable) {
		BatchDto batchFallBack = this.fallBack.getBatchFallBack(bId);

		return batchFallBack;
	}

	@Override
	public Batch saveBatch(Batch batch) {

		return this.repository.save(batch);
	}

	@Override
	public Batch updateBatch(int bId, Batch updateBatch) throws ResourceNotFoundException {

		Batch batch = this.repository.findById(bId)
				.orElseThrow(() -> new ResourceNotFoundException("Batch", "Id", String.valueOf(bId)));

		batch.setBatchTitle(updateBatch.getBatchTitle() == "" ? batch.getBatchTitle() : updateBatch.getBatchTitle());
		// batch.setCourseId(updateBatch.getCourseId());
		batch.setDuration(updateBatch.getDuration() == "" ? batch.getDuration() : updateBatch.getDuration());
		batch.setStartDate(updateBatch.getStartDate() == "" ? batch.getStartDate() : updateBatch.getStartDate());
		batch.setEndDate(updateBatch.getEndDate() == "" ? batch.getEndDate() : updateBatch.getEndDate());
		// batch.setImage(updateBatch.getImage());
		batch.setStatus(updateBatch.getStatus() == "" ? batch.getStatus() : updateBatch.getStatus());
		batch.setTeacherId(updateBatch.getTeacherId() == 0 ? batch.getTeacherId() : updateBatch.getTeacherId());
		batch.setTime(updateBatch.getTime() == "" ? batch.getTime() : updateBatch.getTime());
		return this.repository.save(batch);
	}

	@Override
	public boolean delete(int bId) throws ResourceNotFoundException {

		boolean status = false;
		Batch batch = this.repository.findById(bId)
				.orElseThrow(() -> new ResourceNotFoundException("Batch", "Id", String.valueOf(bId)));
		if (batch != null) {
			this.repository.deleteById(bId);
			status = true;
		}
		return status;
	}

	@Override
	@Retry(name = "getAllBatchFallBack", fallbackMethod = "getAllBatchfallBack")
	public List<BatchDto> getAllBatch(long instituteId) {

		List<Batch> findAll = this.repository.findAllBatchByInstituteId(instituteId);

		List<BatchDto> batchDtoList = new ArrayList<>();

		for (Batch batch : findAll) {
			BatchDto batchDto = new BatchDto();

			Course course = this.restTemplate.getForObject("http://course-service/course/" + batch.getCourseId(),
					Course.class);
			System.out.println("Course of "+course +batch.getCourseId());

			batchDto.setBId(batch.getBId());
			batchDto.setBatchTitle(batch.getBatchTitle());
			batchDto.setStartDate(batch.getStartDate());
			batchDto.setEndDate(batch.getEndDate());
			batchDto.setDuration(batch.getDuration());
			batchDto.setStatus(batch.getStatus());
			batchDto.setLocation(batch.getLocation());
			batchDto.setTime(batch.getTime());
			batchDto.setCourse(course);
			batchDto.setImage(batch.getImage());
			
			//TeacherDto teacher = this.restTemplate
				//	.getForObject("http://teacher-service/teacher/" + batch.getTeacherId(), TeacherDto.class);

			batchDto.setTeacherDto(null);

			batchDtoList.add(batchDto);

		}
		return batchDtoList;
	}

	public List<BatchDto> getAllBatchfallBack(long instituteId, Throwable throwable) {
		return this.fallBack.getAllBatch(instituteId);
	}

	@Override
	@Retry(name = "batchByTId", fallbackMethod = "getBatchByTIdFallBack")
	public List<BatchDto> getAllBatchByTeacherId(int tId) {

		List<Batch> findAllByTeacherId = this.repository.findAllByTeacherId(tId);

		List<BatchDto> batchDtoList = new ArrayList<>();
		for (Batch batch : findAllByTeacherId) {

			BatchDto batchDto = new BatchDto();

			try {
				Course course = this.restTemplate.getForObject("http://course-service/course/" + batch.getCourseId(),
						Course.class);
				batchDto.setCourse(course);
			} catch (Exception e) {
				throw new ServiceFailureException("Course Service is down");
			}

			batchDto.setBId(batch.getBId());
			batchDto.setBatchTitle(batch.getBatchTitle());
			batchDto.setStartDate(batch.getStartDate());
			batchDto.setEndDate(batch.getEndDate());
			batchDto.setDuration(batch.getDuration());
			batchDto.setStatus(batch.getStatus());
			batchDto.setLocation(batch.getLocation());
			batchDto.setTime(batch.getTime());

			batchDto.setImage(batch.getImage());

			try {
				TeacherDto teacher = this.restTemplate
						.getForObject("http://teacher-service/teacher/" + batch.getTeacherId(), TeacherDto.class);
				batchDto.setTeacherDto(teacher);
			} catch (Exception e) {

				throw new ServiceFailureException("Teacher Service is down");
			}

			batchDtoList.add(batchDto);
		}

		return batchDtoList;
	}

	public List<BatchDto> getBatchByTIdFallBack(int teacherId, Throwable throwable) {
		return this.fallBack.getAllBatchByTeacherId(teacherId);
	}

	@Override
	@Retry(name = "findByTitleFallBack", fallbackMethod = "findByTitleFallBack")
	public List<BatchDto> findByBatchTitleContaining(String batchTitle) {

		List<BatchDto> batchDtoList = new ArrayList<>();
		List<Batch> batches = this.repository.findByBatchTitleContaining(batchTitle);

		for (Batch batch : batches) {

			BatchDto batchDto = new BatchDto();

			batchDto.setBId(batch.getBId());
			batchDto.setBatchTitle(batch.getBatchTitle());
			batchDto.setStartDate(batch.getStartDate());
			batchDto.setEndDate(batch.getEndDate());
			batchDto.setDuration(batch.getDuration());
			batchDto.setStatus(batch.getStatus());
			batchDto.setLocation(batch.getLocation());
			batchDto.setImage(batch.getImage());

			try {
				Course course = this.restTemplate.getForObject("http://course-service/course/" + batch.getCourseId(),
						Course.class);

				System.out.println(course);
				batchDto.setCourse(course);
			} catch (Exception e) {
				throw new ServiceFailureException("Course service is down");
			}

			try {
				TeacherDto teacher = this.restTemplate
						.getForObject("http://teacher-service/teacher/" + batch.getTeacherId(), TeacherDto.class);
				batchDto.setTeacherDto(teacher);
			} catch (Exception e) {
				throw new ServiceFailureException("Teacher Service is failed");
			}

			System.out.println("Course Id is " + batch.getCourseId());

			batchDtoList.add(batchDto);

		}
		return batchDtoList;
	}

	public List<BatchDto> findByTitleFallBack(String batchTitle) {
		return this.fallBack.findByBatchTitleContaining(batchTitle);
	}

	@Override
	public List<BatchTitleAndDate> getBatchTitleAndDate(long instituteId) {

		return this.repository.getBatchTitleAndStartDate(instituteId);
	}

	@Override
	public Long countBatchAvailable(long instituteId) {

		return this.repository.countBatchAvailable(instituteId);
	}

	@Override
	public Batch getSingleBatch(int batchId) throws ResourceNotFoundException {

		return this.repository.findById(batchId)
				.orElseThrow(() -> new ResourceNotFoundException("Batch", "Id", String.valueOf(batchId)));
	}

	@Override
	public List<Batch> getBatchesByTeacherId(int tId) {

		System.out.println("In teacher");
		return this.repository.findAllByTeacherId(tId);
	}

	@Override
	public List<Batch> findByCourseId(int courseId) {

		return this.repository.findByCourseId(courseId);
	}

	@Override
	public List<Batch> getBatchByTeacherId(int tId) {

		return this.repository.findAllByTeacherId(tId);
	}
	
	private String getToken() {
	    ServletRequestAttributes attributes = 
	        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

	    if (attributes != null) {
	        HttpServletRequest request = attributes.getRequest();
	        String authHeader = request.getHeader("Authorization");
	        return authHeader; // e.g., "Bearer eyJhbGciOiJIUzI1NiIs..."
	    }
	    return null;
	}

}
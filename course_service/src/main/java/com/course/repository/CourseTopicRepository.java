package com.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.entity.CourseTopic;

public interface CourseTopicRepository  extends JpaRepository<CourseTopic,Long>{

}

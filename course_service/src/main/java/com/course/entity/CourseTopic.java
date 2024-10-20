package com.course.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="CourseTopic")
public class CourseTopic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long topicId;
	private String topicName;
	private String content;
	
	@ManyToOne
    @JoinColumn(name = "cid")
	@JsonBackReference
    private Course course;
	
	 @Override
	    public String toString() {
	        return "CourseTopic{" +
	                "topicId=" + topicId +
	                ", topicName='" + (topicName != null ? topicName : "null") + '\'' +
	                ", content='" + (content != null ? content : "null") + '\'' +
	                ", courseId=" + (course != null ? course.getCid() : "null") + 
	                '}';
	    }

}

package com.course.entity;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
		
	private String courseName;
		
	private String description;
		
	private String skills;
		
	private String fees;	
	
	private String lastUpdatedDate;
	
	private Long instituteId;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	 private List<CourseTopic> topics = new ArrayList<>(); // Ensure the name matches the JSON key

	  @Override
	    public String toString() {
	        return "Course{" +
	                "cid=" + cid +
	                ", courseName='" + (courseName != null ? courseName : "null") + '\'' +
	                ", fees=" + fees +
	                ", instituteId=" + instituteId +
	                ", topics=" + (topics != null ? topics.size() + " topics" : "null") +
	                '}';
	    }

}
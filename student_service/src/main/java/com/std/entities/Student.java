package com.std.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="student")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int stdId;
	private String firstName;
	private String lastName;
	private String passoutYear;
	private int batchId;
	private String lastEducation;
	private String courseName;	
	private String image;
	private Long instituteId;
	private Long totalFees;
	private Long paidFees;
	private Long remainFees;
	public int getStdId() {
		return stdId;
	}
	public void setStdId(int stdId) {
		this.stdId = stdId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassoutYear() {
		return passoutYear;
	}
	public void setPassoutYear(String passoutYear) {
		this.passoutYear = passoutYear;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public String getLastEducation() {
		return lastEducation;
	}
	public void setLastEducation(String lastEducation) {
		this.lastEducation = lastEducation;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(Long instituteId) {
		this.instituteId = instituteId;
	}
	public Long getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(Long totalFees) {
		this.totalFees = totalFees;
	}
	public Long getPaidFees() {
		return paidFees;
	}
	public void setPaidFees(Long paidFees) {
		this.paidFees = paidFees;
	}
	public Long getRemainFees() {
		return remainFees;
	}
	public void setRemainFees(Long remainFees) {
		this.remainFees = remainFees;
	}
	@Override
	public String toString() {
		return "Student [stdId=" + stdId + ", firstName=" + firstName + ", lastName=" + lastName + ", passoutYear="
				+ passoutYear + ", batchId=" + batchId + ", lastEducation=" + lastEducation + ", courseName="
				+ courseName + ", image=" + image + ", instituteId=" + instituteId + ", totalFees=" + totalFees
				+ ", paidFees=" + paidFees + ", remainFees=" + remainFees + "]";
	}
	
	
		 
}

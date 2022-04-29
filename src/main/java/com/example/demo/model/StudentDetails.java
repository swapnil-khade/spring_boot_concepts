package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "student_details")
public class StudentDetails {
	@Id
	@GeneratedValue
	Long id;
	@NotNull
	String name;
	String courseName;
	String feeStatus;
	
	public StudentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public StudentDetails(Long id, String name, String courseName, String feeStatus) {
		super();
		this.id = id;
		this.name = name;
		this.courseName = courseName;
		this.feeStatus = feeStatus;
	}


	public StudentDetails(String name, String courseName, String feeStatus) {
		super();
		this.name = name;
		this.courseName = courseName;
		this.feeStatus = feeStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	@Override
	public String toString() {
		return "\n StudentDetails [id=" + id + ", name=" + name + ", courseName=" + courseName + ", feeStatus=" + feeStatus
				+ "]";
	}
	
	

}

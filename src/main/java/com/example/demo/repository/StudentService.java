package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.model.StudentDetails;


@Repository
public interface StudentService extends JpaRepository<StudentDetails, Long>{
	
	@Query(
			value="select * from student_details where name = :name",
			nativeQuery = true)
	List<StudentDetails> getAllStudents(@Param("name") String name);
	
	StudentDetails findByName(String name);

}

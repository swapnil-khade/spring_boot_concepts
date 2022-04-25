package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.StudentDetails;
import com.example.demo.repository.StudentService;


@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	EntityManager entityManager;

	@GetMapping("/welcome")
	public String show() {
		return "APIs are working";
	}
	
	@GetMapping("/")
	public ResponseEntity<Object> getAll(){
		List<StudentDetails> list = studentService.findAll();
		if(list!=null)
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		return new ResponseEntity<Object>("No records to display", HttpStatus.OK);
	}
	
//	@GetMapping("/")
//	public ResponseEntity<Object> getAll(){														//pagination
//		//entityManager.getTransaction().begin();
//		
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<StudentDetails> cq = cb.createQuery(StudentDetails.class);
//		
//		Root<StudentDetails> studentRoot = cq.from(StudentDetails.class);
//		
//		//cq.select(studentRoot.get("name"));
//		//cq.where(cb.equal(studentRoot.get("name"), "maya"));									//adding restriction Predicate
//		
//		CriteriaQuery<StudentDetails> select = cq.select(studentRoot);
//		
//		TypedQuery<StudentDetails> query = entityManager.createQuery(select);
//		query.setFirstResult(0);
//		query.setMaxResults(2);
//		
//		List<StudentDetails> list = query.getResultList();
//		if(list!=null)
//			return new ResponseEntity<Object>(list, HttpStatus.OK);
//		return new ResponseEntity<Object>("No records to display", HttpStatus.OK);
//	}
	
	@GetMapping("/studentList/{name}")															// using query
	public List<StudentDetails> getAllSt(@PathVariable String name){
		return studentService.getAllStudents(name);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Object> getByID(@PathVariable Long id){
		
		Optional<StudentDetails> result = studentService.findById(id);
		if(result.isPresent())
			return new ResponseEntity<Object>(result.get(), HttpStatus.OK);
		return new ResponseEntity<Object>("record not available for given ID",HttpStatus.OK);		
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<Object> getByName(@PathVariable String name){
		List<StudentDetails> list = studentService.findAll();
		List<StudentDetails> finalList = new ArrayList();
		for (StudentDetails l : list) {
			if(l.getName().equals(name)) {
				finalList.add(l);
			}
		}
		if(!finalList.isEmpty())
			return new ResponseEntity<Object>(finalList, HttpStatus.OK);
		return new ResponseEntity<Object>("No record found with given name", HttpStatus.OK);
	}
	
	@PostMapping("/addStudent")
	public ResponseEntity<Object> addStudent(@RequestBody StudentDetails studentDetails){
		if(studentDetails!=null) {
			 StudentDetails result = studentService.save(studentDetails);
			 return new ResponseEntity<Object>(studentDetails, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("record was not added", HttpStatus.OK);		
		
	}
	
//	@PostMapping("/addStudent")
//	public StudentDetails addStudent(@RequestBody StudentDetails studentDetails){
//		StudentDetails stud = studentService.save(studentDetails);
//		return  stud;
//				
//	}
	@PutMapping("/updateStudent/{id}")
	public ResponseEntity<Object> updateStudent(@PathVariable Long id, @RequestBody StudentDetails student){
		Optional<StudentDetails> result = studentService.findById(id);
		if(result.isPresent()) {
			StudentDetails stud = result.get();
			stud.setName(student.getName());
			stud.setCourseName(student.getCourseName());
			stud.setFeeStatus(student.getFeeStatus());
			studentService.save(stud);
			return new ResponseEntity<Object>(stud, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Record not updated", HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteStudent/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable Long id){
		Optional<StudentDetails> student = studentService.findById(id);
		if(student.isPresent()) {
			studentService.deleteById(id);
			return new ResponseEntity<Object>("record deleted", HttpStatus.OK);
		}
		return new ResponseEntity<Object>(" Record not found with ID: "+id, HttpStatus.OK);	
	}
}

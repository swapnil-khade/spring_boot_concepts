package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.model.StudentDetails;
import com.example.demo.repository.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
class StudentControllerTest {
	
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();				// JSON  <--->  String
	
	@Mock
	StudentService studentService;
	
	@InjectMocks
	StudentController studentController;
	
	StudentDetails student_1 = new StudentDetails(1L,"Swapnil", "java", "paid");
	StudentDetails student_2 = new StudentDetails(2L,"Maya", "java", "paid");
	StudentDetails student_3 = new StudentDetails(3L,"Arav", "java", "paid");
	StudentDetails student_4 = new StudentDetails(4L,"Tushar", "Full stack developer", "paid");
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
	}
	
	

	
	@Test
	public void getAll_success() throws Exception{
		List<StudentDetails> studentList = new ArrayList<StudentDetails>(Arrays.asList(student_1,student_2,student_3,student_4));
		Mockito.when(studentService.findAll()).thenReturn(studentList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/student/").contentType(MediaType.APPLICATION_JSON))		
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[1].name", is("Maya")));
	
	}
	
	@Test
	public void getById_success() throws Exception {
		Mockito.when(studentService.findById(student_1.getId())).thenReturn(java.util.Optional.of(student_1));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/student/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Swapnil")))
				.andDo(print());
		
	}
	
	@Test
	public void addStudent_success() throws Exception{
		StudentDetails std1 = new StudentDetails(1L,"Swapnil", "java", "paid");
		
		Mockito.when(studentService.save(std1)).thenReturn(std1);
		
		String studentStr = objectWriter.writeValueAsString(std1);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student/addStudent")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.content(studentStr);
		
		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Swapnil")));
				
	}
	
	@Test
	public void updateStudent_success() throws Exception{
		StudentDetails std1 = new StudentDetails(1L,"Maya", "java", "paid");
		
		Mockito.when(studentService.findById(student_1.getId())).thenReturn(java.util.Optional.of(student_1));
		Mockito.when(studentService.save(std1)).thenReturn(std1);
		
		String studentStr = objectWriter.writeValueAsString(std1);
		
		MockHttpServletRequestBuilder mckRequest = MockMvcRequestBuilders.put("/student/updateStudent/"+std1.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON)
				.content(studentStr);
		
		mockMvc.perform(mckRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Maya")))
				.andDo(print());
	}
	
	@Test
	public void deleteStudent_success()throws Exception{
		Mockito.when(studentService.findById(student_1.getId())).thenReturn(java.util.Optional.of(student_1));
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/student/deleteStudent/"+student_1.getId()))
				.andExpect(status().isOk());
		
	}

}

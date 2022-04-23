package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.Animal;

@RestController
public class AnimalController {

	@Qualifier("cat")														// Qualifier annotation for removing ambiguity
	@Autowired	
	private Animal animal;
	
	@GetMapping("/")
	public String dispay() {
		return "welcome!";
	}
	
	@GetMapping("/sound")
	public ResponseEntity<Object> animal(){
		String s= animal.sound();
		return new ResponseEntity<Object>(s, HttpStatus.OK);
	}
}

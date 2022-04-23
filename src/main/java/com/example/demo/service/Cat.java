package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class Cat implements Animal {

	@Override
	public String sound() {
		System.out.println("Meow...");
		return "Meow...";
	}

}

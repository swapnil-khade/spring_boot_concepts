package com.example.demo.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
//@Primary																// Primary annotation for removing ambiguity
public class Dog implements Animal {

	@Override
	public String sound() {

		System.out.println("Bark...");
		return "Bark...";
	}

}

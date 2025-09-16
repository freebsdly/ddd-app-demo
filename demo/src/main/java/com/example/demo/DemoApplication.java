package com.example.demo;

import org.example.kb.KnowledgeBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		KnowledgeBase kb = new KnowledgeBase();
		SpringApplication.run(DemoApplication.class, args);
	}

}

package com.demo.inexture.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.inexture.model.School;

@RestController
public class SchoolController {
	
	private static List<School> schoolList = new ArrayList<>();

	
	@Value("${author}") private String author;
	 
	
	@Value("${eureka.instance.instance-id}")
	private String instanceId;
	
	static {
		schoolList.add(new School("Nirma", "Ahmedabad", "CBSE"));
		schoolList.add(new School("AES", "Surat", "GSEB"));
		schoolList.add(new School("Sant Kabir", "Surat", "CBSE"));
		schoolList.add(new School("Loyola", "Ahmedabad", "GSEB"));
		schoolList.add(new School("Udgam", "Surat", "CBSE"));
		schoolList.add(new School("KV", "Ahmedabad", "GSEB"));
		schoolList.add(new School("DPS", "Ahmedabad", "GSEB"));
	}
	
	@GetMapping(value = "/view/all")
	public List<School> findAll(){
		return schoolList;
	}
	
	@RequestMapping(value = "/view/{schoolName}", method = RequestMethod.GET)
	public School findSchool(@PathVariable("schoolName") String schoolName){
		return schoolList.stream().filter(s -> s.getName().equalsIgnoreCase(schoolName)).findFirst().get();
	}
	
	@RequestMapping(value = "/find/board/{boardName}", method = RequestMethod.GET)
	public List<School> findSchoolWithBoard(@PathVariable("boardName") String boardName){
		return schoolList.stream().filter(s -> s.getSchoolBoard().equalsIgnoreCase(boardName)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/find/city/{cityName}", method = RequestMethod.GET)
	public List<School> findSchoolWithCity(@PathVariable("cityName") String cityName){
		return schoolList.stream().filter(s -> s.getCity().equalsIgnoreCase(cityName)).collect(Collectors.toList());
	}
	
	
	@RequestMapping(value = "/author", method = RequestMethod.GET)
	public String author() {
		return author;
	}
	 

	@GetMapping("/hello")
	public String getHelloWordObject() {
		return "Hi there! instance id: " + instanceId;
	}

}

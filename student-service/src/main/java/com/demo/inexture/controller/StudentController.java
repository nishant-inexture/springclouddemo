package com.demo.inexture.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.inexture.model.School;
import com.demo.inexture.model.Student;

@RestController
public class StudentController {
	
	private static List<Student> studentList;
	private static List<School> schoolList;
	
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/view/all", method = RequestMethod.GET)
    public List<Student> findAll(){
    	// Check and Populate data
		populateSchoolAndStudents();
		return studentList;
    }
    
	@RequestMapping(value = "/view/{studentName}", method = RequestMethod.GET)
	public List<Student> findStudentsWithName(@PathVariable(value = "studentName") String studentName){
		// Check and Populate data
		populateSchoolAndStudents();
		return studentList.stream().filter(s -> s.getName().equalsIgnoreCase(studentName)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/city/{city}", method = RequestMethod.GET)
	public List<Student> findStudentsWithCity(@PathVariable(value = "city") String city){
		// Check and Populate data
		populateSchoolAndStudents();
		
		// Find schools in the given city using the School Service
		List<School> schoolsWithCity = restTemplate.exchange("http://school-service/find/city/" + city,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<School>>() {}).getBody();
		
		// Filter Students as per the schools found
		return studentList.stream().filter(s -> schoolsWithCity.contains(s.getSchool())).collect(Collectors.toList());
	}
	
	@RequestMapping(value = "/class/{className}", method = RequestMethod.GET)
	public List<Student> findStudentsWithClass(@PathVariable(value = "className") String className){
		// Check and Populate data
		populateSchoolAndStudents();
		return studentList.stream().filter(s -> s.getClassName().equalsIgnoreCase(className)).collect(Collectors.toList());
	}
	
	// Fetch list of school and then create students - START
	public void populateSchoolAndStudents() {
		if(schoolList == null || schoolList.isEmpty()) {
    		schoolList = restTemplate.exchange("http://school-service/view/all",
    				HttpMethod.GET, null, new ParameterizedTypeReference<List<School>>() {}, schoolList).getBody();
    	}
		if((studentList == null || studentList.isEmpty())  && (schoolList != null && !schoolList.isEmpty())) {
    		populateStudents();
		}
	}
	
	public void populateStudents() {
		studentList = new ArrayList<>();
		School nirmaSchool = schoolList.stream().filter(s -> s.getName().equalsIgnoreCase("nirma")).findFirst().get();
		School loyolaSchool = schoolList.stream().filter(s -> s.getName().equalsIgnoreCase("loyola")).findFirst().get();
		School santKabirSchool = schoolList.stream().filter(s -> s.getName().equalsIgnoreCase("sant kabir")).findFirst().get();
		School kvSchool = schoolList.stream().filter(s -> s.getName().equalsIgnoreCase("kv")).findFirst().get();
		
		studentList.add(new Student("Manthan Patel", nirmaSchool, "10", "A"));
		studentList.add(new Student("Parth Pancholi", nirmaSchool, "10", "B"));
		studentList.add(new Student("Ravi Patel", nirmaSchool, "12", "A"));
		studentList.add(new Student("Nikhil Shah", nirmaSchool, "12", "B"));
		
		studentList.add(new Student("Rahul Shah", loyolaSchool, "10", "A"));
		studentList.add(new Student("Bhavika Soni", loyolaSchool, "10", "B"));
		studentList.add(new Student("Ravi Pancholi", loyolaSchool, "12", "A"));
		studentList.add(new Student("Nishant Singh", loyolaSchool, "12", "B"));

		studentList.add(new Student("Shaunak shastri", santKabirSchool, "10", "B"));
		studentList.add(new Student("Aditya Dave", santKabirSchool, "10", "B"));
		studentList.add(new Student("Sonu Patel", santKabirSchool, "12", "A"));
		studentList.add(new Student("Pankaj Udaas", santKabirSchool, "12", "A"));
		
		studentList.add(new Student("Meet Patel", kvSchool, "10", "B"));
		studentList.add(new Student("Sahil Surani", kvSchool, "10", "B"));
		studentList.add(new Student("Rakshit Shah", kvSchool, "12", "A"));
		studentList.add(new Student("Alpesh Panchal", kvSchool, "12", "A"));
	}
	// Fetch list of school and then create students - END
	
}

package com.demo.inexture.model;

import java.io.Serializable;

public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	private static int idStore = 0;
	
	public Student() {
		this.id=++idStore;
	}
	
	public Student(String name, School school, String className, String division) {
		this.id=++idStore;
		
		this.name = name;
		this.school = school;
		this.className = className;
		this.division = division;
	}

	private Integer id;
	private String name;
	private School school;
	private String className;
	private String division;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", school=" + school + ", className=" + className
				+ ", division=" + division + "]";
	}

	
}

package com.demo.inexture.model;

import java.io.Serializable;

public class School implements Serializable{

	private static final long serialVersionUID = 1L;
	private static int idStore;
	
	public School() {
		this.id=++idStore;
	}
	
	public School(String name, String city, String schoolBoard) {
		this.id=++idStore;
		this.name = name;
		this.city = city;
		this.schoolBoard = schoolBoard;
	}

	private Integer id = 0;
	private String name;
	private String city;
	private String schoolBoard;
	
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSchoolBoard() {
		return schoolBoard;
	}
	public void setSchoolBoard(String schoolBoard) {
		this.schoolBoard = schoolBoard;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if(obj != null && obj instanceof School) {
			isEqual = this.id == ((School) obj).getId();
		}
		return isEqual;
	}
	
	@Override
	public String toString() {
		return "School [name=" + name + ", city=" + city + ", schoolBoard=" + schoolBoard + "]";
	}
	
}

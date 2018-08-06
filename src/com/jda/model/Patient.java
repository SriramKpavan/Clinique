package com.jda.model;

import org.json.simple.JSONObject;

public class Patient {

	public String name;
	public String number;
	public String id;
	public String age;
	JSONObject jo;
	Patient patient;
	
	public Patient() {
		jo = new JSONObject();
	}
	
	public void toPatient(JSONObject obj) {
		patient = new Patient();
		patient.setName((String) obj.get("name"));
		patient.setId((String) obj.get("id"));
		patient.setNumber((String) obj.get("contact"));
		patient.setAge((String) obj.get("age"));
	   }
	
	public Patient convertJSON() {
		return patient;
		}
		   
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
}


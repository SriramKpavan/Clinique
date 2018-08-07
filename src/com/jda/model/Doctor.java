package com.jda.model;

import org.json.simple.JSONObject;

public class Doctor {


	public String name;
	public String id;
	public String specialisation;
	public String availability;
	public long appointments;

	JSONObject jo;
	Doctor doctor;
	
	public Doctor() {
		jo = new JSONObject();
	}
	   
	public void toJSON(Doctor doctor) {
		jo.put("name", doctor.getName());
		jo.put("id", doctor.getId());
		jo.put("availability", doctor.getAvailability());
		jo.put("specialisation", doctor.getSpecialisation());
		jo.put("appointments", doctor.getAppointments());
	}
	
	public JSONObject convertObject() {
		return jo;
	}
	
	public void toDoctor(JSONObject obj) {
		doctor = new Doctor();
		doctor.setName((String) obj.get("name"));
		doctor.setId((String) obj.get("id"));
		doctor.setAvailability((String) obj.get("availability"));
		doctor.setSpecialisation((String) obj.get("specialisation"));
		doctor.setAppointments((long) obj.get("appointments"));
	   }
	
	public Doctor convertJSON() {
		return doctor;
	   }
	   
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSpecialisation() {
		return specialisation;
	}
	
	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}
	
	public String getAvailability() {
		return availability;
	}
	
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
	public long getAppointments() {
		return appointments;
	}

	public void setAppointments(long appointments) {
		this.appointments = appointments;
	}
	
	public String toString() {
		String x = "\n{\n" + "name :" + name + "\n"+ "id :" + id + "\n" + "specialisation :" + specialisation +"\n" + "availability :" + availability +"\n" + "appointments :" + Long.toString(appointments) +"\n" + "}\n";
		return x;
		
	}
}

package com.jda.service;

import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import com.jda.model.Doctor;
import com.jda.model.Patient;
import com.jda.utility.Utility;

public class Appointments {

	Utility utility = new Utility();
	LinkedList<Doctor> doctorList = new LinkedList<Doctor>();
	LinkedList<Patient> patientList = new LinkedList<Patient>();
	LinkedList<JSONObject> appointList = new LinkedList<JSONObject>();
	Patient patient;
	Doctor doctor;
	
	public LinkedList<Doctor> readDoctorFile() throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject jo = new JSONObject();
		jo = (JSONObject) parser.parse(new FileReader("Input//Doctor.json"));
		JSONArray ja = (JSONArray) jo.get("Doctors");
		doctor = new Doctor();
		for(int i =0; i<ja.size(); i++) {
			doctor.toDoctor((JSONObject)ja.get(i));
			doctorList.add(doctor.convertJSON());
		}
		return doctorList;
		
	}
	
	public LinkedList<Patient> readPatientFile() throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject jo = new JSONObject();
		jo = (JSONObject) parser.parse(new FileReader("Input//Patient.json"));
		JSONArray ja = (JSONArray) jo.get("Patients");
		Patient patient = new Patient();
		for(int i =0; i<ja.size(); i++) {
				patient.toPatient((JSONObject) ja.get(i));
				patientList.add(patient.convertJSON());
		}
		return patientList;
	}
	
	public LinkedList<JSONObject> readAppointmentFile() throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject jo = new JSONObject();
		jo = (JSONObject) parser.parse(new FileReader("Input//Appointment.json"));
		JSONArray ja = (JSONArray) jo.get("Appointments");
		for(int i =0; i<ja.size(); i++) {
			appointList.add((JSONObject) ja.get(i));
		}
		return appointList;
	}
	
	public void appointmentTracker(String name) throws Exception {
		System.out.println("Give the name of the id of the doctor the patient wants to meet");
		String id = utility.takeInputString();
		System.out.println("Give the time at which you want to meet the doctor");
		String time = utility.takeInputString();
		int k = 0;
		doctorList = readDoctorFile();
		patientList = readPatientFile();
		appointList = readAppointmentFile();
		JSONObject obj = new JSONObject();
		for(int j =0; j<doctorList.size(); j++) {
			Doctor doctor = new Doctor();
			if(doctorList.get(j).getId().compareToIgnoreCase(id) == 0) {
				doctor = doctorList.get(j);
				String avail = doctor.getAvailability();
				if(avail.equals(time) || avail.equals("Both")) {
					System.out.println("The doctor is seeing patients at that time, but let me check if he is available");
					for(int i =0; i<appointList.size(); i++) {
						JSONObject object = appointList.get(i);
						if(((String) object.get("Doctor id")).compareTo(id) == 0)
							k++;
						}
					if(k<5) {
						System.out.println("The appointment is made");
						obj.put("Patient name", name);
						obj.put("Patient id", searchPatient(name));
						obj.put("Doctor name", searchDoctor(id));
						obj.put("Doctor id", id);
						obj.put("Time", avail);
						obj.put("Patient's turn", Integer.toString(k+1));
						appointList.add(obj);
						saveToFile();
					}	
					else 
						System.out.println("Sorry the doctor will not be seeing anymore patients, can you tell me another day that you can make it?");
					}
				else 
					System.out.println("The doctor doesnt work at that time, will you be comfortable with another doctor?");
			}
			}
	}
	
	public String searchPatient(String name) {
		for(int i=0; i<patientList.size(); i++) {
			if(patientList.get(i).getName().compareToIgnoreCase(name) == 0)
				return patientList.get(i).getId();
		}
		return null;
	}
	
	public String searchDoctor(String id) {
		for(int i=0; i<doctorList.size(); i++) {
			if(doctorList.get(i).getId().compareToIgnoreCase(id) == 0)
				return doctorList.get(i).getName();
		}
		return null;
	}
	
	public void saveToFile() throws Exception {
		JSONObject jo = new JSONObject();
		jo.put("Appointments", appointList);
		PrintWriter pw = new PrintWriter("Input//Appointment.json");
		pw.write(jo.toJSONString());
		pw.flush();
		pw.close();
	}
}


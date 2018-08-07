package com.jda.service;

import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
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
		doctorList = readDoctorFile();
		patientList = readPatientFile();
		appointList = readAppointmentFile();
		JSONObject obj = new JSONObject();
		int i;
		for(i =0; i<patientList.size(); i++) {
			Patient patient = new Patient();
			patient = patientList.get(i);
			if(patient.getName().equals(name)){
				System.out.println("Here is the details of all the doctors available");
				System.out.println(doctorList);
				i = patientList.size() + 1;
				System.out.println("Give the name of the id of the doctor the patient wants to meet");
				String id = utility.takeInputString();
				System.out.println("Give the time at which you want to meet the doctor");
				String time = utility.takeInputString();
				for(int j =0; j<doctorList.size(); j++) {
					Doctor doctor = new Doctor();
					if(doctorList.get(j).getId().compareToIgnoreCase(id) == 0) {
						doctor = doctorList.get(j);
						String avail = doctor.getAvailability();
						long appointment = doctor.getAppointments();
						if((avail.equals(time) || avail.equals("Both")) &&  (appointment<6)) {
							appointment++;
							doctor = doctorList.remove(j);
							doctor.setAppointments(appointment);
							doctorList.add(doctor);
							System.out.println("The doctor is seeing patients at that time, but let me check if he is available");
							System.out.println("The appointment is made");
							obj.put("Patient name", name);
							obj.put("Patient id", patient.getId());
							obj.put("Doctor name", doctor.getName());
							obj.put("Doctor id", id);
							obj.put("Time", avail);
							obj.put("Patient's turn", Long.toString(appointment));
							obj.put("date", LocalDate.now().toString());
							appointList.add(obj);
							updateDoctor();
							saveToFile();	
						}
						else {
							System.out.println("Sorry the doctor will not be seeing anymore patients for today, can you tell me another day that you can make it?");
							int choice = utility.takeInputInteger();
							if(choice == 1) {
								System.out.println("Tell me a date which is convenient to you");
								String date = utility.takeInputString();
								obj.put("Patient name", name);
								obj.put("Patient id", patient.getId());
								obj.put("Doctor name", doctor.getName());
								obj.put("Doctor id", id);
								obj.put("Time", avail);
								obj.put("Patient's turn", Long.toString(appointment));
								obj.put("date", date);
								appointList.add(obj);
								saveToFile();	
							}
							else
								System.out.println("Sorry for the inconvenience, Thank you");
						}
						j = doctorList.size() + 1;
						}
						else 
							System.out.println("There is no such doctor, can you please check again");
					}
			}
		}
		if(i == patientList.size() + 1) {
			System.out.println("New patient adding to the file, give details");
			String age = utility.takeInputString();
			String id = utility.takeInputString();
			String number = utility.takeInputString();
			JSONObject object = new JSONObject();
			object.put("name", name);
			object.put("age", age);
			object.put("id", id);
			object.put("contact", number);
			addPatient();
		}
		}
	
	public void searchDoctor() throws Exception {
		System.out.println("1. Search by name, 2. Search by id, 3. Search by specialisation, 4. Search by availability");
		int choice = utility.takeInputInteger();
		doctorList = readDoctorFile();
		switch(choice) {
		case 1:
			System.out.println("Enter name");
			String input = utility.takeInputString();
			for(int i=0; i<doctorList.size(); i++) {
				if(doctorList.get(i).getName().compareToIgnoreCase(input) == 0) {
					System.out.println(doctorList.get(i));
					return;
				}
			}
			break;
			
		case 2:
			System.out.println("Enter id");
			String input2 = utility.takeInputString();
			for(int i=0; i<doctorList.size(); i++) {
				if(doctorList.get(i).getId().compareToIgnoreCase(input2) == 0) {
					System.out.println(doctorList.get(i));
					return;
				}	
			}
			break;
			
		case 3:
			System.out.println("Enter specialisation");
			String input3 = utility.takeInputString();
			for(int i=0; i<doctorList.size(); i++) {
				if(doctorList.get(i).getSpecialisation().compareToIgnoreCase(input3) == 0) {
					System.out.println(doctorList.get(i));
					return;
				}		
			}
			break;
			
		case 4:
			System.out.println("Enter availability");
			String input4 = utility.takeInputString();
			for(int i=0; i<doctorList.size(); i++) {
				if(doctorList.get(i).getAvailability().compareToIgnoreCase(input4) == 0) {
					System.out.println(doctorList.get(i));
					return;
				}	
			}
			break;
		}	
		System.out.println("Invalid input");
		return;
	}
	
	public void searchPatient() throws Exception {
		System.out.println("1. Search by name, 2. Search by id, 3. Search by number");
		int choice = utility.takeInputInteger();
		patientList = readPatientFile();
		switch(choice) {
		case 1:
			System.out.println("Enter name");
			String input = utility.takeInputString();
			for(int i=0; i<patientList.size(); i++) {
				if(patientList.get(i).getName().compareToIgnoreCase(input) == 0) {
					System.out.println(patientList.get(i));
					return;
				}
			}
			break;
			
		case 2:
			System.out.println("Enter id");
			String input2 = utility.takeInputString();
			for(int i=0; i<patientList.size(); i++) {
				if(patientList.get(i).getId().compareToIgnoreCase(input2) == 0) {
					System.out.println(patientList.get(i));
					return;
				}	
			}
			break;
			
		case 3:
			System.out.println("Enter number");
			String input3 = utility.takeInputString();
			for(int i=0; i<patientList.size(); i++) {
				if(patientList.get(i).getNumber().compareToIgnoreCase(input3) == 0) {
					System.out.println(patientList.get(i));
					return;
				}		
			}
			break;
		}

		System.out.println("Invalid input");
	}
	
	public void updateDoctor() throws Exception {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		for(int i =0; i<doctorList.size(); i++) {
			doctor.toJSON(doctorList.get(i));
			ja.add(doctor.convertObject());
		}
		jo.put("Doctors", ja);
		PrintWriter pw = new PrintWriter("Input//Doctor.json");
		pw.write(jo.toJSONString());
		pw.flush();
		pw.close();
	}
	
	public void addPatient() throws Exception {
		JSONObject jo = new JSONObject();
		jo.put("Patients", patientList);
		PrintWriter pw = new PrintWriter("Input//Patient.json");
		pw.write(jo.toJSONString());
		pw.flush();
		pw.close();
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


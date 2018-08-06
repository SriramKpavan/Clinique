package com.jda.controller;

import com.jda.service.Appointments;
import com.jda.utility.Utility;

public class AppointmentSetter {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Utility utility = new Utility();
		Appointments app = new Appointments();
		System.out.println("1. Search a doctor, 2. Search a patient, 3. Make an appointment");
		int choice;
		do {
			choice = utility.takeInputInteger();
			switch(choice) {
			case 1:
				app.searchDoctor();
				
			case 2:
				app.searchPatient();
				
			case 3:
				System.out.println("Please give me your name");
				String name = utility.takeInputString();
				app.appointmentTracker(name);
			}
		}while(choice != 0);
	}

}


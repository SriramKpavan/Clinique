package com.jda.controller;

import com.jda.service.Appointments;
import com.jda.utility.Utility;

public class AppointmentSetter {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Utility utility = new Utility();
		Appointments app = new Appointments();
		System.out.println("Please give me your name");
		String name = utility.takeInputString();
		app.appointmentTracker(name);
	}

}


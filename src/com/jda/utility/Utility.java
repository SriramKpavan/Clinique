package com.jda.utility;

import java.util.Scanner;

public class Utility {

	Scanner scanner;

	public Utility() {
		scanner = new Scanner(System.in);
	}

	public String takeInputString() {
		String name = scanner.next();
		return name;
	}

	public int takeInputInteger() {
		int n = scanner.nextInt();
		return n;
	}

	public double takeInputDouble() {
		double n = scanner.nextDouble();
		return n;
	}
}

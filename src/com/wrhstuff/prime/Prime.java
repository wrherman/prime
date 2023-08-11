package com.wrhstuff.prime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Prime {

	static ArrayList<Integer> list = new ArrayList<Integer>();

	static int indexPrime = 30;
	static int indexTotal = 100000500;
	static int indexGrand = indexPrime + indexTotal + 5;
	static int indexCurrent = indexTotal;
	static int indexNext = indexTotal;
	static boolean first = true;

	static boolean[] prime = new boolean[indexPrime];
	static char[] total = new char[indexGrand];

	public static void Multiply(boolean[] item) {

		boolean carry = false;
		first = true;

		for (int x = indexCurrent - 1; x >= 0; x--) {
			if (total[x] == '1') {
				if (first) {
					indexNext = x + indexPrime;
					first = false;
				}
				total[x] = '0';
				carry = false;
				for (int y = 0; y < indexPrime; y++) {
					int mix = x + y;
					char value = '0';
					boolean next = false;
					if ((carry == false) && (total[mix] == '0') && (item[y] == false)) {
						value = '0';
						next = false;
					}
					if ((carry == false) && (total[mix] == '0') && (item[y] == true)) {
						value = '1';
						next = false;
					}
					if ((carry == false) && (total[mix] == '1') && (item[y] == false)) {
						value = '1';
						next = false;
					}
					if ((carry == false) && (total[mix] == '1') && (item[y] == true)) {
						value = '0';
						next = true;
					}
					if ((carry == true) && (total[mix] == '0') && (item[y] == false)) {
						value = '1';
						next = false;
					}
					if ((carry == true) && (total[mix] == '0') && (item[y] == true)) {
						value = '0';
						next = true;
					}
					if ((carry == true) && (total[mix] == '1') && (item[y] == false)) {
						value = '0';
						next = true;
					}
					if ((carry == true) && (total[mix] == '1') && (item[y] == true)) {
						value = '1';
						next = true;
					}
					total[mix] = value;
					carry = next;
				}
			}
		}
		indexCurrent = indexNext;
	}

	public static boolean[] Convert(int value) {
		boolean[] list = new boolean[indexPrime];
		for (int x = indexPrime - 1; x >= 0; x--) {
			double test = Math.pow(2, x);
			if (value >= test) {
				value -= Math.pow(2, x);
				list[x] = true;
			}
		}
		return list;
	}

	public static String Show() {
		String value = "";
		for (int x = 0; x < 30; x++) {
			value += " " + total[x];
		}
		return value;
	}

	public static int Print() {

		int x = indexTotal - 1;

		while (total[x] == '0') {
			x = x - 1;
		}

		return x;
	}

	public static void LoadPrimes(String file) {

		try {
			// the file to be opened for reading
			FileInputStream fis = new FileInputStream(file);
			Scanner sc = new Scanner(fis); // file to be scanned
			// returns true if there is another line to read
			while (sc.hasNextLine()) {
				Integer number = Integer.parseInt(sc.nextLine());
			//	System.out.println("load = " + number);
				list.add(number);
			}
			sc.close(); // closes the scanner
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void LoadTotal(String file) {

		try {
			ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(file)));
			total = lines.get(0).toCharArray();
			System.out.println(total.length);
		} catch (IOException e) {
			// Handle a potential exception
		}
	}

	public static void main(String[] args) {

		for (int f = 0; f < total.length; f++) {
			total[f] = '0';
		}
		System.out.println("total1 = " + total.length);

		//LoadTotal("C:\\Users\\willi\\Documents\\Output.txt");
		System.out.println(Show());
		System.out.println("total2 = " + total.length);

		total[0] = '1';

		LoadPrimes("C:\\Users\\willi\\Documents\\Eight_hundred_thousand.txt");
		System.out.println(list.size());
		System.out.println("total = " + total.length);

 
		try {
			File f = new File("C:\\Users\\willi\\Documents\\OutputFinal.txt");
			FileOutputStream fos = new FileOutputStream(f);
			PrintWriter pw = new PrintWriter(fos);

			System.out.println("list = " + list.size());
			System.out.println("index = " + indexNext);

			for (int x = 0; x < list.size(); x++) {
				int me = list.get(x);
				boolean[] temp = Convert(me);
				Multiply(temp);
				if (x % 1000 == 0) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					System.out.println("x = " + x + " Prime = " + list.get(x) + " length = " + indexCurrent + " time = "
							+ dtf.format(now));
				}
			}

			System.out.println(Show());
			System.out.println(Print());

			String s = new String(total);

			pw.write(s);
			pw.flush();
			fos.close();
			pw.close();
			System.out.println("Output Written to file");
		}

		catch (

		IOException ex) {
			ex.printStackTrace();
		}

	}

}

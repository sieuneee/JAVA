package cse3040_hw1_20181611;

import java.util.Scanner;

public class Problem3 {
	public static void main(String[] args) {
		String in1, in2;
		int cnt = 0;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter a text: ");
		in1 = sc.nextLine();
		
		while(true) {
			System.out.print("Enter a letter: ");
			in2 = sc.nextLine();
			if(in2.length()==0 || in2.length()>1) System.out.println("You must enter a single letter.");
			else break;
		}
		
		for(int i=0;i<in1.length();i++) {
			if(in1.charAt(i)==in2.charAt(0)) cnt++;
		}
		
		System.out.printf("There are %d %c's in the text.\n", cnt, in2.charAt(0));
	}
}

package cse3040_hw1_20181611;

import java.util.Scanner;

public class Problem1 {
	public static void main(String[] args) {
		String input;
		int num=0;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("ASCII code teller. Enter a letter: ");
		input = sc.nextLine();
		
		if(input.length()==1) {
			num = input.charAt(0);
			if((num>96 && num<123) || (num>64 && num<91)) System.out.println("The ASCII code of C is "+num+".");
			else System.out.println("You must input a single uppercase or lowercase alphabet."); 
		}
		else System.out.println("You must input a single uppercase or lowercase alphabet.");
	}
}

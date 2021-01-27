package cse3040_hw1_20181611;

import java.util.Scanner;

public class Problem4 {
	public static void main(String[] args) {
		String in1, in2;
		int cnt = 0;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter a text: ");
		in1 = sc.nextLine();
		
		while(true) {
			System.out.print("Enter a string: ");
			in2 = sc.nextLine();
			if(in2.length()==0) System.out.println("You must enter a string.");
			else break;
		}
		
		for(int i=0;i<in1.length();i++) {
			if(in1.charAt(i)==in2.charAt(0)) {
				int n = i;
				int cor = 0;
				for(int j=0;j<in2.length();j++, n++) {
					if(n>=in1.length()) {
						cor = -1;
						break;
					}
					if(in1.charAt(n)==in2.charAt(j)) {
						cor = 1;
						continue;
					}
					else {
						cor = -1;
						break;
					}
				}
				if(cor==1) cnt++;
			}
		}
		System.out.printf("There are %d instances of \"%s\".\n", cnt, in2);
	}
}

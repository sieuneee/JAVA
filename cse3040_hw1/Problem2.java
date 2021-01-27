package cse3040_hw1_20181611;

import java.util.Scanner;
import java.util.Random;

public class Problem2 {
	public static void main(String[] args) {
		int ans = -1;
		int cnt = 0;
		int num = (int)(Math.random()*100);
		Scanner sc = new Scanner(System.in);
		
		while(ans!=num) {
			System.out.print("Guess a number (1-100): ");
			ans = sc.nextInt();
			if(ans<1 || ans>100) System.out.println("Not in range!");
			else if(num>ans) System.out.println("Too Small!");
			else if(num<ans) System.out.println("Too Large!");
			cnt++;		
		}
		if(ans==num) System.out.printf("Correct! Number of guesses: %d\n", cnt);
	}
}

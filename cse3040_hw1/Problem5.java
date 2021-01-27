package cse3040_hw1_20181611;

import java.util.Scanner;
import java.util.Arrays;

public class Problem5 {
	public static void main(String[] args) {
		int[] arr = new int[6];
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter exam scores of each student.");

		int max1=-1, max2=-1, max1Idx=0, max2Idx=0;
		for(int i=1;i<=5;i++) {
			System.out.printf("Score of student %d: ", i);
			arr[i] = sc.nextInt();
			if(max1==-1) {
				max1 = arr[i];
				max1Idx = i;
			}
			else {
				if(arr[i]>max1) {
					max2 = max1;
					max2Idx = max1Idx;
					max1 = arr[i];
					max1Idx = i;
				}
				else if(arr[i]>max2) {
					max2 = arr[i];
					max2Idx = i;
				}
			}
		}
		
		
		System.out.printf("The 1st place is stdent %d with %d points.\n", max1Idx, max1);
		System.out.printf("The 1st place is stdent %d with %d points.\n", max2Idx, max2);
	
	}
}

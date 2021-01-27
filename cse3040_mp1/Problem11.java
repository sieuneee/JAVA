package cse3040_HW3;

public class Problem11 {
	public static void main(String[] args) {
		PalindromeChecker.check("abcde");
		PalindromeChecker.check("abcba");
		PalindromeChecker.check(1234);
		PalindromeChecker.check(12321);
	}
}

class PalindromeChecker {
	static void check(int a) {
		int tmp = 0;
		int input = a;
        if(input < 0 || (input%10 == 0 && input != 0)) {
        	System.out.println(a + " is not a palindrome.");
            return;
        }
        while(input > tmp) {
            tmp = (input % 10) + (tmp * 10);
            input = input/10;
        }
        if((input == tmp) || (input == tmp/10)) {
        	System.out.println(a + " is a palindrome.");
            return;
        }
        else {
        	System.out.println(a + " is not a palindrome.");
            return;
        }	
	}
	static void check(String a) {
		int j = a.length() - 1;
        for (int i = 0; i < a.length() / 2; i++, j--) {
            if (a.charAt(i) != a.charAt(j)) {
            	System.out.println(a + " is not a palindrome.");
            	return;
            }
        }   
        System.out.println(a + " is a palindrome.");
        return;
	}
}
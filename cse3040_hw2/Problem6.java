package cse3040_hw2;

public class Problem6 {
	public static void main(String[] args) {
		IntSequence seq = new FibonacciSequence();
		for(int i=0; i<20; i++) {
			if(seq.hasNext() == false) break;
			System.out.print(seq.next() + " "); 
		}
		System.out.println(" "); 
	}
}

interface IntSequence{
	boolean hasNext();
	int next();
}

class FibonacciSequence implements IntSequence {
	private int num1 = 0;
	private int num2 = 1;
	public boolean hasNext() {
		return true;
	}
	public int next() {
		int result=num1;
		num1=num2;
		num2=result+num1;
		return result;
	}
}

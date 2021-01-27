package cse3040_hw2;

import java.util.Scanner;

public class Problem7 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a positive integer: ");
		String str = in.nextLine();
		int num = Integer.parseInt(str);
		in.close();
		System.out.println("Integer: " + num);
		IntSequenceStr seq = new BinarySequenceStr(num);
		System.out.print("Binary number: ");
		while(seq.hasNext()) System.out.print(seq.next());
		System.out.println(" ");
	}
}

interface IntSequenceStr{
	boolean hasNext();
	int next();
}
class BinarySequenceStr implements IntSequenceStr{
	private int number;
	private int pos;
	public BinarySequenceStr(int num) {
		this.number = num;
		this.pos = 0;
		while(Math.pow(2, this.pos+1)<=number) {
			this.pos++;
		}
	}
	public boolean hasNext() {
		return this.pos!=-1;
	}
	public int next() {
		int result = this.number / (int)Math.pow(2, this.pos);
		this.number %= (int)Math.pow(2, this.pos);
		this.pos--;
		return result;
	}
}

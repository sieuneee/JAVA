package cse3040_HW3;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Problem13 {
	public static void main(String[] args) {
		Text t = new Text();
		if(t.readTextFromFile("input_prob13.txt")) {
			for(char c = 'a'; c <= 'z'; c++) {
				System.out.println(c + ": " + t.countChar(c));
			}
		}
	}
}

class Text{
	private File file;
	private FileReader fileReader;
	private int[] arr;
	Text(){
		this.arr = new int[26];
	}
	boolean readTextFromFile(String str) {
		try {
			this.file = new File(str);
			this.fileReader = new FileReader(file);
			int i=0;
			while((i=fileReader.read())!=-1){
				if(i>=97 && i<=122) this.arr[i-97]++;
				else if(i>=65 && i<=90) this.arr[i-65]++;
			}
		}catch(FileNotFoundException e) {
			System.out.println("Input file not found.");
			return false;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		} finally{ 
			if(fileReader != null)
				try{
					fileReader.close();
				}catch(IOException e){
					e.printStackTrace();
				} 
		}
		return true;
	}
	public int countChar(char c) {
		return this.arr[c-97];
	}
}

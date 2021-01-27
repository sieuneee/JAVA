package cse3040;

import java.util.*;
import java.io.*;

public class Problem16 {
	public static void main(String args[]) {
	ArrayList<Element> list = ElementReader.readElements("input.txt");
	if(list == null) {
		System.out.println("Input file not found.");
		return;
	}
	Collections.sort(list);
	Iterator<Element> it = list.iterator();
	while(it.hasNext()) System.out.println(it.next());
	} 
}

class Element implements Comparable<Element>{
	private String item;
	private double num;
	public Element(String a, double b){
		this.item = a;
		this.num = b;
	}
	public String getItem() {
		return this.item;
	}
	public double getNum() {
		return this.num;
	}
	@Override
	public int compareTo(Element e) {
		if(this.num<e.getNum()) return -1;
		else if(this.num>e.getNum()) return 1;
		else {
			if(this.item.compareTo(e.getItem())<0) return -1;
			else if(this.item.compareTo(e.getItem())>0) return 1;
			else return 0;
		}
	}
	@Override
	public String toString() {
		return this.item + " " + this.num;
	}
}

class ElementReader{
	static ArrayList<Element> readElements(String str){
        File file=null;
        FileReader fileReader=null;
        BufferedReader bufReader=null;
        ArrayList<Element> newList = null;
        try {
        	
            file = new File(str);
            fileReader = new FileReader(file);
            bufReader = new BufferedReader(fileReader);
            newList = new ArrayList<>();
            String line = "";
            while((line=bufReader.readLine())!=null){
               String[] string = line.split("\\s+");
               newList.add(new Element(string[0], Double.parseDouble(string[1])));
            }
            
        }catch(FileNotFoundException e) {
        	return null;
        	//e.printStackTrace();
        }catch(IOException e){
       	 e.printStackTrace();
        } finally{ 
           if(fileReader != null)
              try{
                 fileReader.close();
              }catch(IOException e){
           	   e.printStackTrace();
              } 
        }
        return newList;
	}
}
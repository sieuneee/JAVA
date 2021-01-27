package cse3040_HW3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Problem15 {
   public static void main(String[] args) {
      ArrayList<Item> list = new ArrayList<>();
      boolean rv = MyFileReader.readDataFromFile("input_prob15.txt", list);
      if(rv == false) {
         System.out.println("Input file not found.");
         return;
      }
      for(Item it: list) System.out.println(it);
   }

}

class Item{
   private String item;
   private int num;
   Item(String a){
      this.item = a;
      this.num = 1;
   }
   public void incNum() {
      this.num++;
   }
   public String getItem(){
      return this.item;
   }
   @Override
   public String toString() {
      return this.item+" "+this.num;
   }
   @Override
   public boolean equals(Object o){
      Item temp = (Item)o;
      return this.getItem().equals(temp.getItem());
   }
}

class MyFileReader{
      static boolean readDataFromFile(String str, ArrayList<Item> list) {
         File file=null;
         FileReader fileReader=null;
         BufferedReader bufReader=null;
         try {
            file = new File(str);
            fileReader = new FileReader(file);
            bufReader = new BufferedReader(fileReader);
            String line = "";
            while((line=bufReader.readLine())!=null){
               String[] string = line.split("\\s+"); 
               for(int i=0;i<string.length;i++) {
                  string[i] = string[i].toLowerCase();
                  Item newItem = new Item(string[i]);
               if(list.contains(newItem)) {
                     int index = list.indexOf(newItem);
                     list.get(index).incNum();
                  }
                  else {
                     list.add(newItem);
                  }
               }
            }
         }catch(FileNotFoundException e) {
            return false;
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
         return true;
      }
}
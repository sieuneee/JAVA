package cse3040_HW3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Problem14 {
   public static void main(String[] args) {
      FruitBox box = new FruitBox();
      boolean rv = ItemReader.fileToBox("input_prob14.txt", box);
      if(rv == false) return;
      box.add(new Fruit("orange", 9.99));
      System.out.println("----------------");
      System.out.println("    Summary");
      System.out.println("----------------");
      System.out.println("number of items: " + box.getNumItems());
      System.out.println("most expensive item: " + box.getMaxItem() + " (" + box.getMaxPrice() + ")");
      System.out.println("cheapest item: " + box.getMinItem() + " (" + box.getMinPrice() + ")");
      System.out.printf("average price of items: %.2f", box.getAvgPrice());
      }
}

class ItemReader{
   static boolean fileToBox(String str, FruitBox box) {
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
            box.add(new Fruit(string[0], Double.parseDouble(string[1])));
         }
      }catch(FileNotFoundException e) {
         System.out.println("Input file not found.");
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

class Fruit{
   private String fu;
   private double cost;
   Fruit(String a, double b){
      this.fu = a;
      this.cost = b;
      System.out.println(this.fu + " " + this.cost);
   }
   public String retStr() {
      return this.fu;
   }
   public double retCost() {
      return this.cost;
   }
}

class FruitBox{
   private ArrayList<Fruit> fru = new ArrayList<>();

   void add(Fruit fruit) {
      this.fru.add(fruit);
   }
   
   int getNumItems() {
      return this.fru.size();
   }
    Fruit getFruit(int arg){
        Fruit targetFruit = this.fru.get(0);
        for(Fruit f : this.fru){
            if(f.retCost() * arg > targetFruit.retCost() * arg){
                targetFruit = f;
            }
        }
        return targetFruit;
        
    }
   String getMaxItem() {
       return this.getFruit(1).retStr();
   }
   double getMaxPrice() {
       return this.getFruit(1).retCost();
   }
   String getMinItem() {
       return this.getFruit(-1).retStr();
   }
   double getMinPrice() {
       return this.getFruit(-1).retCost();
   }
   double getAvgPrice() {
       double sum = 0;
       for(Fruit f : this.fru){
            sum += f.retCost();
        }
        return sum / this.getNumItems();
   }
}

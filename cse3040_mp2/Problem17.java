package cse3040;

import java.io.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Iterator;

public class Problem17 { 
    public static void main(String args[]) {
      Map<String, Double> map = MapManager.readData("input.txt");
      if(map == null) {
         System.out.println("Input file not found."); 
         return;
      }
      System.out.println(map);
   }
}

class MapManager{
   static Map<String, Double> readData(String str) {
      File file=null;
        FileReader fileReader=null;
        BufferedReader bufReader=null;
        Map<String, Double> newMap = null;
        
        try {
           newMap = new NewMap<>();
            file = new File(str);
            fileReader = new FileReader(file);
            bufReader = new BufferedReader(fileReader);
            
            String line = "";
            while((line=bufReader.readLine())!=null){
               String[] string = line.split("\\s+");
               newMap.put(string[0], Double.parseDouble(string[1]));
            }
            
        }catch(FileNotFoundException e) {
           //e.printStackTrace();
           return null;
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
         
        return newMap;
   }

}

class NewMap<K, V> extends TreeMap<K, V>{
    @Override
    public String toString(){
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext())
            return "";

        StringBuilder sb = new StringBuilder();
        for (;;) {
            Map.Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key);
            sb.append(" ");
            sb.append(value);
            if (! i.hasNext())
                return sb.toString();
            sb.append("\n");
        }
    }
}
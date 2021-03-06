package cse3040;

import java.io.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


public class Problem18 { 
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
        List<Map.Entry<String, Double>> list =  null;
        Map<String, Double> sortedMap = null;
        
        try {
           newMap = new HashMap<>();
            file = new File(str);
            fileReader = new FileReader(file);
            bufReader = new BufferedReader(fileReader);
            
            String line = "";
            while((line=bufReader.readLine())!=null){
               String[] string = line.split("\\s+");
               newMap.put(string[0], Double.parseDouble(string[1]));
            }
            list = new LinkedList<>(newMap.entrySet());
            
            Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                	if(o1.getValue()>o2.getValue()) return 1;
                	else if(o1.getValue()<o2.getValue()) return -1;
                	else{
                		if(o1.getKey().compareTo(o2.getKey())>0) return 1;
                		else if(o1.getKey().compareTo(o2.getKey())<0) return -1;
                		return 0;
                	}
                }
            });

            sortedMap = new NewMap<>(); 
            for(Iterator<Map.Entry<String, Double>> iter = list.iterator(); iter.hasNext();){
                Map.Entry<String, Double> entry = iter.next();
                sortedMap.put(entry.getKey(), entry.getValue());
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
         
        return sortedMap;
   }
   
}
class NewMap<K, V> extends LinkedHashMap<K, V>{
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
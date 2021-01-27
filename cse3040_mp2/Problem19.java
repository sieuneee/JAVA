package cse3040;

import java.util.*;
import java.io.*;
import java.net.*;

public class Problem19 {
   public static void main(String[] args) {
      ArrayList<BookInfo> books;
      books = BookReader.readBooks("https://www.barnesandnoble.com/b/books/_/N-1fZ29Z8q8");
      Collections.sort(books);
      for (int i = 0; i < books.size(); i++) {
         BookInfo book = books.get(i);
         System.out.println(book);
      }
   }
}

class BookInfo implements Comparable<BookInfo>{
   private int num;
   private String price;
   private String title;
   private String author;

   BookInfo(int num, String price, String title, String author) {
      this.num = num;
      this.price = price;
      this.title = title;
      this.author = author;
   }

   @Override
   public int compareTo(BookInfo b){
      return b.num - this.num;
   }

   @Override
   public String toString(){
      return "#"+this.num + " "+ this.title + ", "+ this.author+", "+this.price;
   }
}

class BookReader {
   static ArrayList<BookInfo> readBooks(String str) {
      ArrayList<BookInfo> books = new ArrayList<>();
      URL url = null;
      BufferedReader input = null;
      String address = str;
      String line = "";
      
      List<String> lines = new ArrayList<>();
      try {
         url = new URL(address);
         input = new BufferedReader(new InputStreamReader(url.openStream()));
         while ((line = input.readLine()) != null) {
            if(0 < line.trim().length())
               lines.add(line);
         }
         input.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      int rank = 1;
      String tempTitle = "";
      String tempAuthor = "";
      String tempPrice = "";
      for(int i = 0; i<lines.size(); i++){
         String l = lines.get(i);
         if(l.contains("product-info-title")){
            String titleLine = lines.get(i+1);
            int from = titleLine.indexOf(">") + ">".length();
            int to = titleLine.indexOf("</a><span");
            tempTitle = titleLine.substring(from, to);
         }
         if(l.contains("product-shelf-author")){
            int tt = l.indexOf(">by") + ">by".length();
            int from = l.indexOf("\">", tt) + "\">".length();
            int to = l.indexOf("</a>", from);
            tempAuthor = l.substring(from, to);
         }
         if(l.contains("current link")){
            int from = l.indexOf("\">") + "\">".length();
            int to = l.indexOf("</a></span>");
            tempPrice = l.substring(from, to);

            books.add(new BookInfo(rank++, tempPrice, tempTitle, tempAuthor));
         }
      }

      return books;
   }
}
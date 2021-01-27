package cse3040;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.*;

public class Problem20 {
	public static void main(String[] args) {
		ArrayList<BookInfo> books;
		books = BookReader.readBooksJsoup("https://www.barnesandnoble.com/b/books/_/N-1fZ29Z8q8");
		Collections.sort(books);
		for (int i = 0; i < books.size(); i++) {
			BookInfo book = books.get(i);
			System.out.println(book);
		}
	}
}

class BookInfo implements Comparable<BookInfo> {
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
	public int compareTo(BookInfo b) {
		return b.num - this.num;
	}

	@Override
	public String toString() {
		return "#" + this.num + " " + this.title + ", " + this.author + ", " + this.price;
	}
}

class BookReader {
	static ArrayList<BookInfo> readBooksJsoup(String str) {
		ArrayList<BookInfo> books = new ArrayList<>();

		Document doc = null;
		try {
			doc = Jsoup.connect(str).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int rank = 1;
		
		Elements title = doc.select("h3.product-info-title").select("a[title]");
		Elements author = doc.select("div.product-shelf-author a:first-of-type").select("a[href]");
		Elements price = doc.select("a.current");

		for(int i=0;i<title.size();i++)
			books.add(new BookInfo(rank++, price.eq(i).text(), title.eq(i).text(), author.eq(i).text()));

		return books;
	}
}
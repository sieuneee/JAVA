package cse3040fp;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

	HashMap<String, DataOutputStream> clients;

	Server(){
		clients = new HashMap<>();
		Collections.synchronizedMap(clients);
	}

	public static void main(String[] args) {
		if(args.length !=1) {
			System.out.println("Please give the port number as an argument.");
			System.exit(0);
		}
		int portNum = Integer.parseInt(args[0]); 
		new Server().start(portNum);
	}

	public void start(int portNum) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(portNum);
			if(BookInfo.readBooksFromFile("books.txt") % 2 == 1){
				System.out.println("Error about reading book file");
			}
			while(true) {
				socket = serverSocket.accept();
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	class ServerReceiver extends Thread{
		Socket socket;
		DataInputStream r = null;
		DataOutputStream w = null;

		ServerReceiver(Socket socket){
			this.socket = socket;
			try {
				r = new DataInputStream(socket.getInputStream());
				w = new DataOutputStream(socket.getOutputStream());

			} catch(IOException e) {}
		}

		//@Override
		public void run() {
			try{
				while(true){
					try{
						String inputData = null;
						inputData = r.readUTF();

						String type = XMLparser(inputData, "type");
						if(type.equals("add")){
							if(BookInfo.addBook(
									XMLparser(inputData, "title"),
									XMLparser(inputData, "author")    
									) == 0){
								StringBuilder sb = XMLgen("type", "add");
								sb = XMLgen(sb, "code", "0");
								w.writeUTF(sb.toString());                                
							}
							else{
								StringBuilder sb = XMLgen("type", "add");
								sb = XMLgen(sb, "code", "1");
								w.writeUTF(sb.toString());                                
							}
							w.flush();
							BookInfo.synchFile();
						}   
						else if(type.equals("borrow")){
							BookInfo tempBook = BookInfo.borrowBook(
									XMLparser(inputData, "title"),
									XMLparser(inputData, "borrower")
									);
							if(tempBook != null){
								StringBuilder sb = XMLgen("type", "borrow");
								sb = XMLgen(sb, "code", "0");
								sb = XMLgen(sb, "title", tempBook.getTitle());
								w.writeUTF(sb.toString());
							}
							else{
								StringBuilder sb = XMLgen("type", "borrow");
								sb = XMLgen(sb, "code", "1");
								w.writeUTF(sb.toString());
							}
							w.flush();
							BookInfo.synchFile();
						}
						else if(type.equals("return")){
							BookInfo tempBook = BookInfo.returnBook(
									XMLparser(inputData, "title"),
									XMLparser(inputData, "borrower")
									);
							if(tempBook != null){
								StringBuilder sb = XMLgen("type", "return");
								sb = XMLgen(sb, "code", "0");
								sb = XMLgen(sb, "title", tempBook.getTitle());
								w.writeUTF(sb.toString());
							}
							else{
								StringBuilder sb = XMLgen("type", "return");
								sb = XMLgen(sb, "code", "1");
								w.writeUTF(sb.toString());
							}
							w.flush();
							BookInfo.synchFile();
						}
						else if(type.equals("info")){
							List<BookInfo> bookList = BookInfo.infoBook(
									XMLparser(inputData, "borrower")
									);
							StringBuilder sb2 = new StringBuilder();
							for(BookInfo b : bookList){
								sb2.append("<book>");
								sb2.append(b.getTitle());
								sb2.append(", ");
								sb2.append(b.getAuthor());
								sb2.append("</book>");
							}
							StringBuilder sb = XMLgen("type", "info");
							sb = XMLgen(sb, "code", "0");
							sb = XMLgen(sb, "count", Integer.toString(bookList.size()));
							sb = XMLgen(sb, "list", sb2.toString());
							w.writeUTF(sb.toString());
							w.flush();
						}
						else if(type.equals("search")){
							List<BookInfo> bookList = BookInfo.searchBook(
									XMLparser(inputData, "keyword")
									);
							StringBuilder sb2 = new StringBuilder();
							for(BookInfo b : bookList){
								sb2.append("<book>");
								sb2.append(b.getTitle());
								sb2.append(", ");
								sb2.append(b.getAuthor());
								sb2.append("</book>");
							}
							StringBuilder sb = XMLgen("type", "search");
							sb = XMLgen(sb, "code", "0");
							sb = XMLgen(sb, "count", Integer.toString(bookList.size()));
							sb = XMLgen(sb, "list", sb2.toString());
							w.writeUTF(sb.toString());
							w.flush();
						}
					}
					catch(SocketException e){
						break;
					}
				}

				w.close();
				r.close();

			}catch(IOException e) {
				//e.printStackTrace();
			}
		}
	}

	static StringBuilder XMLgen(String tag, String content){
		StringBuilder sb = new StringBuilder();
		sb.append("<"); sb.append(tag); sb.append(">");
		sb.append(content);
		sb.append("</"); sb.append(tag); sb.append(">");
		return sb;
	}

	static StringBuilder XMLgen(StringBuilder appended, String tag, String content){
		appended.append("<"); appended.append(tag); appended.append(">");
		appended.append(content);
		appended.append("</"); appended.append(tag); appended.append(">");
		return appended;
	}

	static String XMLparser(String origin, String tag){

		StringBuilder sb = new StringBuilder();
		sb.append("<"); sb.append(tag); sb.append(">");
		String openTag = sb.toString();
		sb = new StringBuilder();
		sb.append("</"); sb.append(tag); sb.append(">");
		String closeTag = sb.toString();
		return origin.substring(
				origin.indexOf(openTag) + openTag.length(),
				origin.indexOf(closeTag)
				);
	}
}


class BookInfo implements Comparable<BookInfo> {
	private String title;
	private String author;
	private String borrower;

	static File file = null;
	static BufferedReader br = null;
	static ArrayList<BookInfo> books = new ArrayList<>();

	static int synchFile(){ // Synchnonize between books ArrayList and File
		Collections.sort(books);
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(BookInfo.file));
			StringBuilder sb = new StringBuilder();
			for(Iterator<BookInfo> itr = books.iterator(); itr.hasNext(); ){
				BookInfo book = itr.next();
				sb.append(book.title); sb.append("\t");
				sb.append(book.author); sb.append("\t");
				sb.append(book.borrower); 
				if(itr.hasNext())
					sb.append("\n");
			}
			bw.append(sb.toString());
			bw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return 0;
	}

	static int readBooksFromFile(String fileName){
		if(file != null)
			return 2;
		try{
			BookInfo.file = new File(fileName);
			br = new BufferedReader(new FileReader(BookInfo.file));                
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			return 1;
		}
		catch(Exception e){
			e.printStackTrace();
			return 3;
		}
		// END OF FILE CONNECTING
		if(br != null){
			String line = "";
			try{
				while((line=br.readLine())!=null){
					String[] string = line.split("\t");
					books.add(new BookInfo(string[0], string[1], string[2]));
				}
			}
			catch(IOException e){
				return 5;
			}
		}
		return 0;
	}

	static int addBook(String newBookTitle, String newBookAuthor) {

		for(BookInfo b : books){
			if(b.titleComparator(newBookTitle)){ // true ==> There is a same title book
				return 1;
			}
		}
		books.add(new BookInfo(newBookTitle, newBookAuthor, "-"));
		return 0;
	}

	static BookInfo borrowBook(String title, String borrower){
		for(BookInfo b : books){
			if(b.titleComparator(title) && b.getBorrower().equals("-")){   
				b.setBorrower(borrower);
				return b;
			}
		}
		return null;
	}

	static BookInfo returnBook(String title, String borrower){
		for(BookInfo b : books){
			if(b.titleComparator(title) && b.getBorrower().equals(borrower)){
				b.setBorrower("-");
				return b;
			}
		}
		return null;
	}

	static List<BookInfo> infoBook(String borrower){
		List<BookInfo> ret = new ArrayList<>();
		for(BookInfo b : books){
			if(b.getBorrower().equals(borrower)){
				ret.add(b);
			}
		}
		Collections.sort(ret);
		return ret;
	}

	static List<BookInfo> searchBook(String keyword){
		List<BookInfo> ret = new ArrayList<>();
		for(BookInfo b : books){
			if(0 <= b.getAuthor().indexOf(keyword) || 0 <= b.getTitle().indexOf(keyword)){
				ret.add(b);
			}
		}
		Collections.sort(ret);
		return ret;
	}
	// END OF STATIC METHOD

	BookInfo(String title, String author, String borrower) {
		this.title = title;
		this.author = author;
		this.borrower = borrower;
	}

	String getTitle(){
		return this.title;
	}

	String getAuthor(){
		return this.author;
	}

	String getBorrower(){
		return this.borrower;
	}

	int setBorrower(String borrower){
		this.borrower = borrower;
		return 0;
	}

	boolean titleComparator(String title){
		return this.title.equalsIgnoreCase(title);
	}

	boolean checkBookAvailable(){
		return this.borrower.equals("-");
	}

	@Override
	public String toString() {
		return  this.title + ", " + this.author + ", " + this.borrower;
	}

	@Override
	public int compareTo(BookInfo b){
		return this.title.compareToIgnoreCase(b.title);
	}
}
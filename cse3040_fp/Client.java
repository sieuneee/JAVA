package cse3040fp;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	public static void main(String[] args){
		Socket sock = null;
		BufferedReader br = null;

	      if(args.length<2) {
	          System.out.println("Please give the IP address and port number as arguments.");
	          System.exit(0);
	       }
	      
			InputThread it = new InputThread(sock, br);
			it.start(args[1]);
	}
}

class InputThread extends Thread {
	private Socket sock = null;
	private BufferedReader br = null;
	public InputThread(Socket sock, BufferedReader br){
		this.sock = sock;
		this.br = br;
	}

	public void start(String str) {
		Scanner sc = new Scanner(System.in);    
		try{
			Socket socket = new Socket( InetAddress.getLocalHost() , Integer.valueOf(str));

			DataOutputStream w = new DataOutputStream(socket.getOutputStream());
			DataInputStream r = new DataInputStream(socket.getInputStream());

			while(true){

				System.out.print("Enter userID>> ");
				String user = sc.nextLine();

				if ("exit".equals(user)) {
					socket.close();
					w.close();
					r.close();
					break;
				}

				boolean flag=true;
				if(user.length()==0) flag=false;
				for(int i=0;i<user.length();i++) {
					int idx = user.charAt(i);
					if(idx==' ') flag=false;
					if(!((idx>=97 && idx<=122) || (idx>=48 && idx<=57))) flag=false;
				}
				if(flag==false) 
					System.out.println("UserID must be a single word with lowercase alphabets and numbers.");
				else {
					System.out.println("Hello "+ user);

					// START USER COMMAND
					while(true) {
						System.out.print(user+">> ");
						String cmd = sc.nextLine();

						if("add".equals(cmd)) {
							System.out.print("add-title> ");
							String title = sc.nextLine();
							if(!title.equals("")) {
								System.out.print("add-author> ");
								String author = sc.nextLine();
								if(!author.equals("")) {
									StringBuilder sb = XMLgen("type", "add");
									sb = XMLgen(sb, "title", title);
									sb = XMLgen(sb, "author", author);
									w.writeUTF(sb.toString());
									w.flush();
									// END OF SENDING REQUEST
									// START OF GETTING RESPONSE
									String res = r.readUTF();
									if(
											XMLparser(res, "code").equals("0")
											){
										System.out.println("A new book added to the list.");
									}
									else{
										System.out.println("The book already exists in the list.");
									}
								}
							}
						}
						else if("borrow".equals(cmd)) {
							System.out.print("borrow-title> ");
							String title = sc.nextLine();
							if(!title.equals("")){
								StringBuilder sb = XMLgen("type", "borrow");
								sb = XMLgen(sb, "title", title);
								sb = XMLgen(sb, "borrower", user);
								w.writeUTF(sb.toString());
								w.flush();
								// END OF SENDING REQUEST
								// START OF GETTING RESPONSE
								String res = r.readUTF();
								if(
										XMLparser(res, "code").equals("0")
										){
									System.out.print("You borrowed a book. - ");
									System.out.println(XMLparser(res, "title"));
								}
								else{
									System.out.println("The book is not available.");
								}
							}
						}
						else if("return".equals(cmd)) {
							System.out.print("return-title> ");
							String title = sc.nextLine();
							if(!title.equals("")){
								StringBuilder sb = XMLgen("type", "return");
								sb = XMLgen(sb, "title", title);
								sb = XMLgen(sb, "borrower", user);
								w.writeUTF(sb.toString());
								w.flush();
								// END OF SENDING REQUEST
								// START OF GETTING RESPONSE
								String res = r.readUTF();
								if(
										XMLparser(res, "code").equals("0")
										){
									System.out.print("You returned a book. - ");
									System.out.println(XMLparser(res, "title"));
								}
								else{
									System.out.println("You did not borrow the book.");
								}
							}
						}
						else if("info".equals(cmd)) {
							StringBuilder sb = XMLgen("type", "info");
							sb = XMLgen(sb, "borrower", user);
							w.writeUTF(sb.toString());
							w.flush();
							// END OF SENDING REQUEST
							// START OF GETTING RESPONSE
							String res = r.readUTF();
							System.out.print("You are currently borrowing ");
							System.out.print(XMLparser(res, "count"));
							System.out.println(" books:");

							int count = Integer.parseInt(XMLparser(res, "count"));
							String list = XMLparser(res, "list");
							if(count != 0){
								int fromIndex = list.indexOf("<book>");
								for(int i = 1; i<=count; i++){
									System.out.print(Integer.toString(i)); System.out.print(". ");
									System.out.println(XMLparser(list, "book", fromIndex));
									fromIndex = list.indexOf("<book>", fromIndex+1);
								}
							}
						}
						else if("search".equals(cmd)) {
							while(true){
								System.out.print("search-title> ");
								String keyword = sc.nextLine();
								if(keyword.equals(""))
									break;
								if(keyword.length() < 3){
									System.out.println("Search string must be longer than 2 characters.");
								}
								else{
									StringBuilder sb = XMLgen("type", "search");
									sb = XMLgen(sb, "keyword", keyword);
									w.writeUTF(sb.toString());
									w.flush();
									// END OF SENDING REQUEST
									// START OF GETTING RESPONSE
									String res = r.readUTF();
									System.out.print("Your search matched ");
									System.out.print(XMLparser(res, "count"));
									System.out.println(" results.");
									int count = Integer.parseInt(XMLparser(res, "count"));
									String list = XMLparser(res, "list");
									if(count != 0){
										int fromIndex = list.indexOf("<book>");
										for(int i = 1; i<=count; i++){
											System.out.print(Integer.toString(i)); System.out.print(". ");
											System.out.println(XMLparser(list, "book", fromIndex));
											fromIndex = list.indexOf("<book>", fromIndex+1);
										}
									}
									break;
								}
							}
						}
						else if("exit".equals(cmd)) {
							break;
						}
						else {
							System.out.println("[available commands]");
							System.out.println("add: add a new book to the list of books.");
							System.out.println("borrow: borrow a book from the library.");
							System.out.println("return: return a book to the library.");
							System.out.println("info: show list of books I am currently borrowing.");
							System.out.println("search: search for books.");
						}

					}

				}
			}

			socket.close();
			w.close();
			r.close();
		}catch(Exception ex){
			System.out.println("Connection establishment failed.");
		}finally{
			try{
				if(br != null) br.close();
			}catch(Exception ex) {}
			try{
				if(sock != null) sock.close();
			}catch(Exception ex) {}
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

	static String XMLparser(String origin, String tag, int fromIndex){

		StringBuilder sb = new StringBuilder();
		sb.append("<"); sb.append(tag); sb.append(">");
		String openTag = sb.toString();
		sb = new StringBuilder();
		sb.append("</"); sb.append(tag); sb.append(">");
		String closeTag = sb.toString();
		return origin.substring(
				origin.indexOf(openTag, fromIndex) + openTag.length(),
				origin.indexOf(closeTag, fromIndex)
				);
	}

}

import java.net.*;
import java.io.*;
import java.util.*;

/* Pake algoritma nosql distributed database, metode ring server*/

public class Tester {
	private	static Struktur S;

	public static void printTest(ArrayList<Data> D) {
		Iterator<Data> it = D.iterator();
		while(it.hasNext())
		{
		    Data obj = it.next();
		    System.out.println(obj.getData());
		}
	}

	public static void main(String[] args){
		/*
		S = new Struktur();
		S.createTable("ganteng");
		S.insertData("ganteng","001","Habibie Ganteng");
		S.insertData("ganteng","002","Habibie Ganteng2");
		S.createTable("ghabibie");
		printTest(S.getAllDataFromTable("ghabibie"));
		*/
		Scanner reader = new Scanner(System.in);
		System.out.println("1. NameNode, 2. DataNode, 3. Client");
		System.out.print("Silahkan masukkan kode mode program diatas : "); int a = reader.nextInt();
		if (a == 1) {
			try {
				new ListServer(); //list dari semua database server
				ServerSocket serverSocket = new ServerSocket(2014);	
				int i = 1;	
				while (i <= 100)
				{
					new trackers(serverSocket).start();
					i++;		
				}
				System.out.println("Listening peer @ "+serverSocket.getLocalPort());
			}catch (Exception e){e.printStackTrace();}
		} else if (a == 2) {
			reader.nextLine();
			System.out.print("IP Address server : "); String IP = reader.nextLine();
			new trackerc(IP);
		} else if (a == 3) {
			reader.nextLine();
			System.out.print("IP Address server : "); String IP = reader.nextLine();
			Client C = new Client(IP);
		}
	}
}

import java.net.*;
import java.io.*;
import java.util.*;

/* Cara kerja server2 :
	1) Ketika konek, lakukan registrasi server ke ListServer dan lakukan migrasi (apabila perlu)
	2) Saat pembagian data, siapapun boleh mulai dari server 1 .. n
	3) disimpan, sebuah tabel dan data disimpan dimana saja itu dicatat (yang dicatat adalah id server (mulai dari 0))
	4) Saat data sebuah tabel akan diambil, buka hashmap diatas untuk pencarian id server. karena cuma display_all, lakukan concatenasi
	5) Concatenasi berhasil, kirim balik ke client */

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
		System.out.println("1. Server, 2. Server Tambahan, 3. Client");
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

		}
	}
}

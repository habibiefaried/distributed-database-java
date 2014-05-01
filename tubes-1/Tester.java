import java.util.*;

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
		System.out.println("1. Server, 2. Client");
		System.out.print("Silahkan masukkan kode mode program diatas : "); int a = reader.nextInt();
		if (a == 1) {
			Server S = new Server();
			S.start();
		} else if (a == 2) {
			reader.nextLine();
			System.out.println("IP Target : "); String IP = reader.nextLine();
			Client C = new Client(IP);
		}
	}
}

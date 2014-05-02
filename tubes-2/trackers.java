import java.net.*;
import java.io.*;
import java.util.*;

//Disini ada 2 jenis thread :
// thread_client & thread_server

public class trackers extends Thread {
	private ServerSocket serverSocket;
	private Protokol P;
	private maximum_data = 10; //hanya bisa menyimpan 10 data biar keliatan

	public trackers(ServerSocket S) {
		this.serverSocket = S;
		P = new Protokol();
	}
	
	public void run() {
		try {
			String data; String command;
			Socket server = serverSocket.accept();
			P = new Protokol(server);
			command = P.recv();
			if (command.equals("client")) { //pengambilan data
				
			} else if (command.equals("server")) { //untuk server stuff, migrasi, dll
				System.out.println("Terhubung ke client : " + server.getRemoteSocketAddress());
				P.send("1u0090aishhdh21");
			} else P.send("apa ini?");

		}catch (Exception e) { e.printStackTrace(); }
	}
}

import java.net.*;
import java.io.*;
import java.util.*;

//Disini ada 2 jenis thread :
// thread_client & thread_server

public class trackers extends Thread {
// ini merupakan namenode
	private ServerSocket serverSocket;
	private Protokol P;
	private int maximum_data = 10; //hanya bisa menyimpan 10 data biar keliatan

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
				if (ListServer.getJmlServer() == 0)
					P.send("NO-DATANODE");
				else {
					P.send("Anda terdaftar sebagai client kami, selamat datang");
					while (true) {
						command = P.recv();
						if ((command.equals("NULL")) || (command.equals("quit"))) break;
						else {
						//diambil dari server pertama doang (percobaan)
							Protokol PR = ListServer.getProtokolServer(0);
							PR.send(command);
							String recv_datanode = PR.recv();
							if (recv_datanode.equals("REPEAT")) {
								ArrayList<String> resp = PR.repeatedRecv();
								for (int i = 0; i < resp.size(); i++) {
									System.out.println(resp.get(i));
								}
								System.out.println(PR.recv());
								P.sendRepeatMessage(resp);
								P.send("DONE");
							}
							else P.send(recv_datanode); //meneruskan data
						}
					}
				}			
			} else if (command.equals("server")) { //untuk server stuff, migrasi, dll
				System.out.println("Terhubung ke client : " + server.getRemoteSocketAddress());
				P.send(ListServer.getTop().toString()); //kirim jumlah server as ID
				ListServer.addServer(P,ListServer.getTop()); //disimpan juga dalam database
			} else P.send("apa ini?");

		}catch (Exception e) { e.printStackTrace(); }
	}
}

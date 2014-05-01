import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread {
	private ServerSocket serverSocket;
	private Protokol P;
	private Struktur S;

	public Server() {
		try {
			serverSocket = new ServerSocket(2014);
			S = new Struktur();
		}catch (Exception e){e.printStackTrace();}
	}	

	public void run() {
		while(true) 
		{
			try {
				String recv_commands="";

				System.out.println("Listening on port " +serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept(); //socket
				P = new Protokol(server); //kelas protokol initialized
				System.out.println("Terhubung dengan "+ server.getRemoteSocketAddress());
	
				while (!recv_commands.equals("quit")) {
					recv_commands = P.recv();
					if (recv_commands.equals("quit")) P.send("server quits");
					else P.send(operateDatabase(recv_commands,P));
					System.out.println("DEBUG : "+recv_commands);
	
					if (recv_commands.equals("NULL")) break; //fault tolerance
				}	

				server.close(); //jika sudah quit maka tutup
				System.out.println("Terputus dengan "+ server.getRemoteSocketAddress());

			}catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			}catch (IOException e) {
				e.printStackTrace();
				break;
			} catch (Exception e) { System.out.println("test"); }
		}
	}

	private String operateDatabase(String command, Protokol P) {
		ArrayList<String> commands = new ArrayList<String>();
		System.out.println("Client> "+command);
		for (String retval: command.split(" ",4)){
			commands.add(retval);
		}
	
		if (commands.get(0).equals("create")) {
			if (commands.size() != 3) return "FALSE-COMMAND"; //command tidak sesuai
			else {
				if (S.createTable(commands.get(2))) return "OK"; //berhasil
				else return "FALSE-EXISTS"; //sudah exists
			}
		} else if (commands.get(0).equals("insert")) {
			if (commands.size() != 4) return "FALSE-COMMAND"; //command tidak sesuai
			else {
				if (S.insertData(commands.get(1),commands.get(2),commands.get(3))) return "OK"; //OK
				else return "FALSE-NO-TABLE/KEY-NOT-VALID"; //tidak ada tabel atau key invalid ( > 0)
			}		
		}
		else if (commands.get(0).equals("display")) {
			if (commands.size() != 2) return "FALSE-COMMAND"; //salah command
			else { P.sendRepeatMessage(S.getAllDataFromTableStr(commands.get(1),false)); return "OK"; } //ambil data dari tabel
		}
		else if (commands.get(0).equals("display_all")) {
			if (commands.size() != 2) return "FALSE-COMMAND"; //salah command
			else { P.sendRepeatMessage(S.getAllDataFromTableStr(commands.get(1),true)); return "OK"; } //ambil data dari tabel
		}
		else return "FALSE";
	}
}

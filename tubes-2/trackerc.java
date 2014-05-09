import java.net.*;
import java.io.*;
import java.util.*;

public class trackerc {
//Ini merupakan datanode
	private Protokol P;
	private Integer ID; //ID server berupa hash

	public trackerc (String IP) {
		new Struktur();
		try {
			String command="";
			Socket client = new Socket(IP, 2014);

			System.out.println("Terhubung ke server : " + client.getRemoteSocketAddress());
			P = new Protokol(client);
			P.send("server"); //konfirmasi terlebih dahulu
			ID = Integer.parseInt(P.recv());
			logging(ID.toString()); 

			while (true) {
				//Idealnya gk pernah keluar dari sini
				command = P.recv(); //menunggu command terus2an
				P.send(operateDatabase(command,P));
				if (command.equals("NULL")) break;
			}
			System.out.println("Terputus dengan namenode server");

		} catch (Exception e) {e.printStackTrace();}
	}

	private void logging(String logs) {
		System.out.println("[DEBUG] "+logs);
	}

	private String operateDatabase(String command, Protokol P) {
		ArrayList<String> commands = new ArrayList<String>();
		System.out.println("DataNode> "+command);
		for (String retval: command.split(" ",5)){
			commands.add(retval);
		}	
				
		if (commands.get(0).equals("create")) {
			if (commands.size() != 3) return "FALSE-COMMAND"; //command tidak sesuai
			else {
				if (Struktur.createTable(commands.get(2))) return "OK"; //berhasil
				else return "FALSE-EXISTS"; //sudah exists
			}
		} else if (commands.get(0).equals("insert")) {
			if (commands.size() != 5) return "FALSE-COMMAND"; //command tidak sesuai
			else {
				if (Struktur.insertData(commands.get(1),commands.get(2),commands.get(3),Integer.parseInt(commands.get(4)))) return "OK"; //OK
				else return "FALSE-NO-TABLE/KEY-NOT-VALID"; //tidak ada tabel atau key invalid ( > 0)
			}		
		}
		else if (commands.get(0).equals("display")) {
			if (commands.size() != 2) return "FALSE-COMMAND"; //salah command
			else { P.sendRepeatMessage(Struktur.getAllDataFromTableStr(commands.get(1),false)); return "OK"; } //ambil data dari tabel
		}
		else if (commands.get(0).equals("display_all")) {
			if (commands.size() != 2) return "FALSE-COMMAND"; //salah command
			else { P.sendRepeatMessage(Struktur.getAllDataFromTableStr(commands.get(1),true)); return "OK"; } //ambil data dari tabel
		} else if (commands.get(0).equals("isExists")) {
			if (Struktur.isExists(commands.get(1),commands.get(2))) return "TRUE";
			else return "FALSE";
		} else if (commands.get(0).equals("DATABASE")) {
			P.sendRepeatMessage(Struktur.getAllTable()); //kirim semua tabel
			return "";
		} else if (commands.get(0).equals("DATA")) {
			P.sendMigrateData(Struktur.getCorrespondData(Integer.parseInt(commands.get(1)),Integer.parseInt(commands.get(2))));
			return "OK";
		}
		else return "COMMAND-NOT-RECOGNIZED";
	}
}

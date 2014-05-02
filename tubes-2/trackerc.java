import java.net.*;
import java.io.*;
import java.util.*;

public class trackerc {
	private Protokol P;
	private Struktur S;
	private String ID; //ID server berupa hash

	public trackerc (String IP) {
		try {
			S = new Struktur();

			String command="";
			Socket client = new Socket(IP, 2014);

			System.out.println("Terhubung ke server : " + client.getRemoteSocketAddress());
			P = new Protokol(client);
			P.send("server"); //konfirmasi terlebih dahulu
			ID = P.recv();
			logging(ID); 

			while (true) {
				command = P.recv(); //menunggu command terus2an
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	private void logging(String logs) {
		System.out.println("[DEBUG] "+logs);
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

import java.net.*;
import java.io.*;
import java.util.*;

//Disini ada 2 jenis thread :
// thread_client & thread_server
//Yang kurang :
// 1. Migrasi data klo ada tambahan server
// 2. Fault Tolerance (server sengaja dimodarin, dll)
// 3. insert, jika key sama. server yang menyimpan bisa beda. jadi bisa ada 2 key (UDAH SOLVE)

public class trackers extends Thread {
// ini merupakan namenode
	private ServerSocket serverSocket;
	private Protokol P;
	private int maximum_data = 10; //hanya bisa menyimpan 10 data biar keliatan
	private ArrayList<Integer> usedToken;

	public trackers(ServerSocket S) {
		this.serverSocket = S;
		P = new Protokol();
		usedToken = new ArrayList<Integer>();
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
					P.send("Anda terdaftar sebagai client kami, ada "+ListServer.getJmlServer()+" datanode yang aktif");
					while (true) {
						command = P.recv();
						if ((command.equals("NULL")) || (command.equals("quit"))) break;
						else operateClientCommand(command,P);
					}
				}			
			} else if (command.equals("server")) { //untuk server stuff, migrasi, dll
				System.out.println("Terhubung ke client : " + server.getRemoteSocketAddress());
				P.send(ListServer.getTop().toString()); //kirim jumlah server as ID
				ListServer.addServer(P,ListServer.getTop()); //disimpan juga dalam database
			} else P.send("apa ini?");

		}catch (Exception e) { e.printStackTrace(); }
	}

	private int getRandomToken() {
		int number = 0; //awalnya dikasih 0
		//berdasarkan max data pool diatas (10)
		Random randomGenerator = new Random();
		while (usedToken.contains(number)) {
			//apabila token sudah ada (isContains, maka random terus hingga ketemu)
			number = randomGenerator.nextInt(maximum_data);
		}
		System.out.println("Nomor Token : "+number);
		return number;
	}

	private int storeDataDecision(int token) {
		//Penentuan data disimpan kemana berdasarkan token
		//return ID Server
		int id_server=0; //defaultnya di 0
		int JmlServer = ListServer.getJmlServer();
		int pool_server = (Integer) (maximum_data / JmlServer); //misal maximum_data 10, jml server = 2. maka masing-masing nampung 5
		System.out.println("Pool Server : "+pool_server);
		/* Cara kerja 
		   Misalkan Token ID Data = 9. Iterate dari pool_server dari ID paling kecil, apakah memenuhi syarat
		   (ID_Server+1) * pool_server > ID Data. Jika iya, maka itu yang akan bertanggung jawab
		   Jika tidak, mundur ke ID yang lebih kecil, lakukan hal yang sama */
		int i = 0;
		while (i<ListServer.getJmlServer()) {
			if ((i+1) * pool_server > token) return i;
			else i++;
		}
		return 0;
	}	

	private void operateClientCommand(String command, Protokol Client) {
		//Disini semua operasi client dikerjakan.
		ArrayList<String> commands = new ArrayList<String>();
		System.out.println("Client> "+command);
		for (String retval: command.split(" ",4)){
			commands.add(retval);
		}
		
		if (commands.get(0).equals("create")) {
			//Jika buat table, maka kirim ke semua server
			boolean isOK = true;
			String error="";
			for (int i = 0; i < ListServer.getJmlServer(); i++) {
				Protokol P = ListServer.getProtokolServer(i); //ambil protokol
				P.send(command); //send command
				String ok = P.recv();
				if (!ok.equals("OK")) { isOK = false; error = ok;}
			}
			if (isOK) Client.send("OK");
			else Client.send(error);

		} else if (commands.get(0).equals("insert")) {
			int token_data = getRandomToken();
			int server_decision;
			int isDataExists = this.checkDataExists(commands.get(2), commands.get(1));

			if (isDataExists == -1) server_decision = storeDataDecision(token_data);
			else server_decision = isDataExists;

			Protokol P = ListServer.getProtokolServer(server_decision);
			P.send(command); //send command
			String reply = P.recv();
			Client.send(reply); //meneruskan ke client

			if (reply.equals("OK") && (isDataExists==-1)) usedToken.add(token_data); //jika data ok dan bukan query update, tambahin di list

			System.out.println("Data disimpan di : "+server_decision);

		} else if (commands.get(0).equals("display")) {
			ArrayList<String> allResp = new ArrayList<String>();
			for (int i = 0; i < ListServer.getJmlServer(); i++) {
				Protokol P = ListServer.getProtokolServer(i); //ambil protokol
				P.send(command); //send command	
				String rp = P.recv(); //permintaan repeat (PASTI)
				ArrayList<String> resp = P.repeatedRecv();
				allResp.addAll(resp);
				System.out.println(P.recv());
			}
			Client.sendRepeatMessage(allResp); //meneruskan ke client
			Client.send("DONE"); //meneruskan ke client

		} else if (commands.get(0).equals("display_all")) {
			ArrayList<String> allResp = new ArrayList<String>();
			for (int i = 0; i < ListServer.getJmlServer(); i++) {
				Protokol P = ListServer.getProtokolServer(i); //ambil protokol
				P.send(command); //send command	
				String rp = P.recv(); //permintaan repeat (PASTI)
				ArrayList<String> resp = P.repeatedRecv();
				if (resp.size() != 0) resp.add("Data diatas dari IP : "+P.getRemoteSocketAddress());
				allResp.addAll(resp);
				System.out.println(P.recv());
			}
			Client.sendRepeatMessage(allResp); //meneruskan ke client
			Client.send("DONE"); //meneruskan ke client

		} else Client.send("False Command");
	}

	private int checkDataExists(String key, String table) {
		//mengembalikan id server yang memiliki data yang sama
		//mengembalikan -1 apabila tidak ada server yang memiliki data tsb
		for (int i = 0; i < ListServer.getJmlServer(); i++) {
			Protokol P = ListServer.getProtokolServer(i);
			P.send("isExists "+key+" "+table);
			String re = P.recv();
			System.out.println("Reply : "+re+". Data = "+key+"-"+table);
			if (re.equals("TRUE")) return i; //jika ada maka mengembalikan id server
		}
		return -1;
	}
}

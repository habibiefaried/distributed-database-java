import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	private Protokol P;

	public Client(String S, boolean debug_mode) {
		new TestingUnit();
		int currentUnit = 0;
		try {
			String command="";
			Socket client = new Socket(S, 2014);
			System.out.println("Terhubung ke server : " + client.getRemoteSocketAddress());

			P = new Protokol(client);
			Scanner reader = new Scanner(System.in);
			
			P.send("client");
			String data = P.recv();
			if (data.equals("NO-DATANODE")) System.out.println("Tidak ada datanode yang sedang aktif, tidak bisa melayani permintaan anda");
			else {
				System.out.println(data);
				while (!command.equals("quit")) {
					
					if ((debug_mode) && (currentUnit < TestingUnit.unitTest.size())) {
						System.out.println("lolSqlTester> "+TestingUnit.unitTest.get(currentUnit));
						command = TestingUnit.unitTest.get(currentUnit);
						currentUnit++;
					}else { System.out.print("lolSql> "); command = reader.nextLine(); }

					P.send(command);
					String response = P.recv();
					if (response.equals("NULL")) {System.out.println("Anda terputus dengan database server"); break;}
					else {
						if (response.equals("REPEAT")) {
							ArrayList<String> resp = P.repeatedRecv();
							for (int i = 0; i < resp.size(); i++) {
								System.out.println(resp.get(i));
							}
						}else System.out.println(response);
					}
				}
			}
		}catch (Exception e) {e.printStackTrace();}
	}
}

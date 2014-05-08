import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	private Protokol P;

	public Client(String S) {
		try {
			String command="";
			Socket client = new Socket(S, 2014);
			System.out.println("Terhubung ke server : " + client.getRemoteSocketAddress());

			P = new Protokol(client);
			Scanner reader = new Scanner(System.in);
			
			while (!command.equals("quit")) {
				System.out.print("lolSql> "); command = reader.nextLine();
				P.send(command);
				String response = P.recv();
				if (response.equals("REPEAT")) {
					ArrayList<String> resp = P.repeatedRecv();
					for (int i = 0; i < resp.size(); i++) {
						System.out.println(resp.get(i));
					}
					System.out.println(P.recv());
				}else System.out.println(response);
			}
		}catch (Exception e) {e.printStackTrace();}
	}
}

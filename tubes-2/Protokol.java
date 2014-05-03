import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Protokol {
	private Socket So;

	public Protokol() {
		this.So = null;
	}

	public String getRemoteSocketAddress(){return So.getRemoteSocketAddress().toString();}
	public int getPort(){return So.getPort();}

	public Protokol(Socket so) {
		this.So = so;
	}

	public void send(String S) {
		//Prosedur ini untuk mengirimkan data berupa string
		try {
		   DataOutputStream out =  new DataOutputStream(So.getOutputStream());
		   out.writeUTF(S);		
		}	
		catch (Exception e) {
		   System.out.println(e.getMessage());
		}
	}

	public String recv() {
		//Prosedur ini untuk menerima data berupa string, mengembalikan string
		try {
			DataInputStream in = new DataInputStream(So.getInputStream());
			return in.readUTF();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "NULL";
	}

	public ArrayList<String> repeatedRecv() {
		//Dimulai dengan message REPEAT dari recv()
		//Akan terus menerima hingga mendengar perintah DONE
		ArrayList<String> Messages = new ArrayList<String>();	
		String pesan = this.recv();
		while (!pesan.equals("DONE")) {
			Messages.add(pesan);
			pesan = this.recv();
		}
		return Messages;
	}

	public void sendRepeatMessage(ArrayList<String> Messages) {
		this.send("REPEAT");
		if (Messages != null) {
			for (int i = 0; i < Messages.size(); i++) {
				this.send(Messages.get(i));
			}
		}
		this.send("DONE");
	}
}

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
			//System.out.println("[DEBUG recv()] "+in.readUTF());
			return in.readUTF();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "NULL";
	}

	private boolean isInteger(String s) {
		Scanner sc = new Scanner(s.trim());
		if(!sc.hasNextInt(10)) return false;
		// we know it starts with a valid int, now make sure
		// there's nothing left!
		sc.nextInt(10);
		return !sc.hasNext();
	}

	public ArrayList<MigrateData> recvMigrateData(){
		/* Berhenti while(true) apabila ada yang mengirim null */
		try {
			String r;
			while (true) { //penanganan apabila ada data yang "bocor"
				r = recv();
				if (isInteger(r)) break;
			}
			//System.out.println("[DEBUG recv] "+r);
			Integer jmlData = Integer.parseInt(r); //terima dulu brp data yang dikirim
			
			ArrayList<MigrateData> allData = new ArrayList<MigrateData>();
			for (int i=0;i<jmlData;i++) {
				ObjectInputStream inStream = new ObjectInputStream(So.getInputStream());
				allData.add((MigrateData) inStream.readObject());
			}
			return allData;
		}catch (Exception e) {
			System.out.println("error pada recvMigrateData()");
		}
		return null;
	}

	public void sendMigrateData(ArrayList<MigrateData> am) {
		try {
			send(new Integer(am.size()).toString()); //kirim dulu brp jumlah data yang dikirim
			for (int i=0;i<am.size();i++) {
				ObjectOutputStream outputStream = new ObjectOutputStream(So.getOutputStream());
				outputStream.writeObject(am.get(i));
			}
		}catch (Exception e){ System.out.println(e.getMessage()); }
	}

	public ArrayList<String> repeatedRecv() {
		//Dimulai dengan message REPEAT dari recv()
		//Akan terus menerima hingga mendengar perintah DONE
		ArrayList<String> Messages = new ArrayList<String>();	
		String r = "0"; //nilai default
		while (true) { //penanganan apabila ada data yang "bocor"
			r = recv(); //terima data
			//System.out.println("[DEBUG recvRepeatMessage()] "+r);
			if (r == "NULL") break; //berarti ada yang putus
			if (isInteger(r)) break;
		}

		Integer jmlData = Integer.parseInt(r); //terima dulu brp data yang dikirim
		for (int i=0;i<jmlData;i++) {
			String pesan = recv();
			//System.out.println("[DEBUG recvRepeatMessage()] "+pesan);
			Messages.add(pesan);
		}
		return Messages;
	}

	public void sendRepeatMessage(ArrayList<String> Messages) {
		if (Messages != null) {
			send(new Integer(Messages.size()).toString()); //kirim dulu brp jumlah data yang dikirim
			for (int i = 0; i < Messages.size(); i++) {
				//System.out.println("[DEBUG sendRepeatMessage()] "+Messages.get(i));
				send(Messages.get(i));
			}
		} else send("0"); //jika null, maka 0
		//System.out.println("[DEBUG sendRepeatMessage()] Selesai mengirim semua");
	}
}

class MigrateData implements Serializable { //ini buat migrasi data
	private static final long serialVersionUID = 5950169519310163575L;
	public String Nama_Tabel;
	public String Key;
	public String Value;
	public String TimeStamp;
	public boolean isPrint; //jika true maka akan ditampilkan, jika false maka tidak akan di print
	public int Token;

	public MigrateData(String tbl, String Key, String Value, String TimeStamp, boolean isPrint, int Token) {
		this.Nama_Tabel = tbl;
		this.Key = Key;
		this.Value = Value;
		this.TimeStamp = TimeStamp;
		this.isPrint = isPrint;
		this.Token = Token;
	}
}

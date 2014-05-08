import java.net.*;
import java.io.*;
import java.util.*;

class ServerData {

	public Protokol P;
	public int ID;

	public ServerData(Protokol _P, int _T) {
		P = _P;
		ID = _T;
	}
}

public class ListServer {
	private static ArrayList<ServerData> SD;
	private static Integer top;

	public ListServer() {
		top = 0;
		SD = new ArrayList<ServerData>();
	}

	public static void addServer(Protokol P, int Token) {
		SD.add(new ServerData(P,Token));
		top++;
	}

	public static Integer getJmlServer(){return SD.size();}
	public static Protokol getProtokolServer(int i){return SD.get(i).P;}

	public static ArrayList<ServerData> getAllServer(){return SD;}

	public static Integer getTop(){return top;}
	public static void deleteServer(int i){SD.remove(i);}
}

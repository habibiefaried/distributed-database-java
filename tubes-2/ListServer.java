import java.net.*;
import java.io.*;
import java.util.*;

class ServerData {

	private static Protokol P;
	private static int Token;

	public ServerData(Protokol _P, int _T) {
		P = _P;
		Token = _T;
	}

}

public class ListServer {
	private static ArrayList<ServerData> SD;
	
	public ListServer() {
		SD = new ArrayList<ServerData>();
	}

	public static void addServer(Protokol P, int Token) {
		SD.add(new ServerData(P,Token));
	}

	public static int getJmlServer(){return SD.size();}
}

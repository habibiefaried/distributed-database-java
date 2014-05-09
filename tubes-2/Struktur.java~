import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.sql.Timestamp;
import java.util.Date;

public class Struktur {	
	private static Hashtable<String,ArrayList<Data>> database; //key : table, value : Data

	private static void replaceKeyExists(String table,String Key) {
		ArrayList<Data> db = getAllDataFromTable(table);
		if (db == null) {}
		else {
			for (int i = 0; i < db.size(); i++) {
				if (db.get(i).Key.equals(Key)) db.get(i).setNoPrint();
			}
		}
	}

	public Struktur() {
		database  = new Hashtable<String,ArrayList<Data>>();
	}

	public static boolean createTable(String table) {
		//jangan lupa nanti divalidasi
		if (database.get(table) == null) {
			ArrayList<Data> D = new ArrayList<Data>();
			database.put(table,new ArrayList<Data>());
			D.add(new Data("0","0","0"));
			database.put(table,D);
			return true;
		}
		else return false;
	}

	public static boolean insertData(String table, String Key, String Value,int Token) {
		ArrayList<Data> D = database.get(table);
		if (Key.equals("0")) return false;
		else {
			if (D == null) return false; //masih tidak ada table
			else {
				replaceKeyExists(table,Key); //coba kalo ada key doble
				D.add(new Data(Key,Value,Token));
				database.put(table,D);
				return true;
			}
		}
	}

	public static boolean insertMigratedData(String table, String Key, String Value,int Token,String TimeStamp, boolean isPrint) {
		//Insert data khusus migrate
		ArrayList<Data> D = database.get(table);
		if (Key.equals("0")) return false;
		else {
			if (D == null) return false; //masih tidak ada table
			else {
				replaceKeyExists(table,Key); //coba kalo ada key doble
				D.add(new Data(Key,Value,TimeStamp,isPrint,Token));
				database.put(table,D);
				return true;
			}
		}
	}

	public static ArrayList<Data> getAllDataFromTable(String table) {
		//System.out.println("Contents : "+database.get(table));
		return database.get(table);
	}

	public static ArrayList<String> getAllDataFromTableStr(String table, boolean print_all) {
		ArrayList<Data> db = getAllDataFromTable(table);
		if (db == null) return null;
		else {
			System.out.println("[DEBUG getAllDataFromTableStr()] Terpanggil : "+db.size());
			ArrayList<String> hasil = new ArrayList<String>();
			for (int i = 0; i < db.size(); i++) {
				if (print_all) hasil.add(db.get(i).getAllData());
				else {
					if (db.get(i).isPrint) hasil.add(db.get(i).getData());
					else {} //No print
				}
			}
			return hasil;
		}
	}

	public static boolean isExists(String key, String table) {
		ArrayList<Data> db = getAllDataFromTable(table);
		if (db == null) {
			System.out.println("[NULL] masuk sini?");
			return false;
		}
		else {
			System.out.println("debug : "+key+"-"+table);
			for (int i = 0; i < db.size(); i++) {
				if (db.get(i).Key.equals(key)) return true;
			}
			return false;
		}
	}

	public static ArrayList<String> getAllTable() {
		ArrayList<String> ret = new ArrayList<String>();
		Enumeration<String> enumKey = database.keys();
		while(enumKey.hasMoreElements()) {
			String keyElm = enumKey.nextElement();
			System.out.println(keyElm);
			ret.add(keyElm);
		}
		return ret;
	}

	public static ArrayList<MigrateData> getCorrespondData(int id_server, int max_pool_server) {
		ArrayList<MigrateData> ret = new ArrayList<MigrateData>();		
		
		int min_data = id_server * max_pool_server;
		int max_data = ((id_server + 1) * max_pool_server)-1;

		Enumeration<String> enumKey = database.keys();
		while(enumKey.hasMoreElements()) { //enumerate nama tabel database
			String keyElm = enumKey.nextElement();
			System.out.println("[MIGRATE DEBUG] "+keyElm);

			ArrayList<Data> elmData = database.get(keyElm);
			int i=0;
			while (i < elmData.size()) {
				Data D = elmData.get(i);
				if (((D.Token < min_data) || (D.Token > max_data)) && (D.Token != -1)) { //jika token diluar range server skrg
					System.out.println("[SELECTED MIGRATE DEBUG] "+D.getAllData()); //debug
					ret.add(new MigrateData(keyElm, D.Key, D.Value ,D.TimeStamp ,D.isPrint, D.Token)); //masukin ke data migrate
					elmData.remove(i); //data dihapus dari database disini (karena akan dipindahkan dan diluar range)
				} else i++; //naikkan iterasi apabila tidak ada yang akan dihapus
			}
		}
		return ret;
	}
}

class Data {
	public String Key;
	public String Value;
	public String TimeStamp;
	public boolean isPrint; //jika true maka akan ditampilkan, jika false maka tidak akan di print
	public int Token;

	private String currentTimeStamp() {
		java.util.Date date= new java.util.Date();
		return (new Timestamp(date.getTime())).toString();
	}

	public Data(String Key, String Value, String tm) {
		this.Key = Key;
		this.Value = Value;
		this.TimeStamp = tm;
		this.isPrint = false;
		this.Token = -1;
	}

	public Data(String Key, String Value, int Token) {
		this.Key = Key;
		this.Value = Value;
		this.Token = Token;
		this.isPrint = true;
		this.TimeStamp = this.currentTimeStamp();
	}

	public Data(String Key, String Value, String TimeStamp, boolean isPrint, int Token) {
		this.Key = Key;
		this.Value = Value;
		this.Token = Token;
		this.TimeStamp = TimeStamp;
		this.Token = Token;
	}

	public void setNoPrint(){this.isPrint = false;}
	public String getData(){return "<"+Key+","+Value+","+TimeStamp+">";}
	public String getAllData(){return "<"+Key+","+Value+","+TimeStamp+",HASH-"+Token+">";}
}

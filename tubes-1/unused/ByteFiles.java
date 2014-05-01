import java.io.*;

/* Kelas ini digunakan untuk melakukan penulisan & pembacaan data kedalam file */
/* Ini program WACANA :v :v :v :v */
public class ByteFiles {

	private String FILE_NAME = "data.dat";

	/*
	public static void main(String... aArgs) {
		BytesStreamsAndFiles test = new BytesStreamsAndFiles();
		//byte[] fileContents = test.read(INPUT_FILE_NAME);
		//test.write(fileContents, OUTPUT_FILE_NAME);
	}*/

	public byte[] serialize(Object obj) throws IOException { //convert objek ke byte array
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		return b.toByteArray();
	}

	public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException { //convert byte array ke objek
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return o.readObject();
	}

  	/** membaca sebuah binary file, return byteArray */
	public byte[] read(String aInputFileName){
		log("Reading in binary file named : " + aInputFileName);
		File file = new File(aInputFileName);
		log("File size: " + file.length());
		byte[] result = new byte[(int)file.length()];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while(totalBytesRead < result.length){
					int bytesRemaining = result.length - totalBytesRead;
					int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
					if (bytesRead > 0){
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
			log("Num bytes read: " + totalBytesRead);
			}
			finally {
				log("Closing input stream.");
				input.close();
			}
		}
		catch (FileNotFoundException ex) {
			log("File not found.");
		}
		catch (IOException ex) {
			log(ex);
		}
		return result;
	}
  
 	/* Tulis binary data ke file, input merupakan byteArary */
	public void write(byte[] aInput, String aOutputFileName){
		log("Writing binary file...");
		try {
			OutputStream output = null;
			try {
				output = new BufferedOutputStream(new FileOutputStream(aOutputFileName));
				output.write(aInput);
			}
			finally {
				output.close();
				}
		}
		catch(FileNotFoundException ex){
			log("File not found.");
		}
		catch(IOException ex){
			log(ex);
		}
	}

	private static void log(Object aThing){
		System.out.println(String.valueOf(aThing));
	}
} 

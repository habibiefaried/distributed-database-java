import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TestingUnit {
	public static ArrayList<String> unitTest;
	public TestingUnit() {
		unitTest = new ArrayList<String>();
		FileInputStream fis = null;
       		BufferedReader reader = null;
		try {
			fis = new FileInputStream("test.txt");
			reader = new BufferedReader(new InputStreamReader(fis));

			System.out.println("Reading Unit Test...");

			String line="test";
			while(true){
				line = reader.readLine();
				if (line==null) break;
				unitTest.add(line);
				//System.out.println("[UNITTEST] "+line);
			}          

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();

		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

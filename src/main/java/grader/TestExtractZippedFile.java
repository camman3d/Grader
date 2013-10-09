package grader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TestExtractZippedFile {
	public static final String ZIPPED_FOLDER = "D:/dewan_backup/Java/RecursionLimit.zip";
//	public static final String ZIPPED_FOLDER = "D:/dewan_backup/Java/AmandaKaramFinalUpdated.zip";

	public static final String ZIP_FILE = "RecursionLimit/src/main/Main.java";

	public static BufferedReader  toBufferedReader(String aZipfolder, String aZipFile) {
		try {
			ZipFile zipFile = new ZipFile(aZipfolder);
			ZipEntry zipEntry = zipFile.getEntry(aZipFile);
			InputStream inputStream = zipFile.getInputStream(zipEntry);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));
			return bufferedReader;

			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void readZipFile() {
		try {
			ZipFile zipFile = new ZipFile(ZIPPED_FOLDER);
			ZipEntry zipEntry = zipFile.getEntry(ZIP_FILE);
			InputStream inputStream = zipFile.getInputStream(zipEntry);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));

			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null)
				System.out.println(inputLine);
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void main (String[] args) {
		readZipFile();
	}
	
	

}

package grader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class TestReadURL {
	final static String RUBRICK = "http://www.cs.unc.edu/~dewan/comp401/current/Recitations/rubrics/rubricA3.html";
	public static void printRubrick() {
		try {
		URL rubrickURL = new URL(RUBRICK);
        BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(rubrickURL.openStream()));

        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null)
            System.out.println(inputLine);
        bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

		
	public static void main (String[] args) {
		printRubrick();		
	}

}

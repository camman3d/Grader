package grader.file;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.misc.Common;


public class FileProxyUtils {
	static public String toText(FileProxy f) {
		if (f == null) {
			// System.out.println();
			return null;
		}
		StringBuffer sb = new StringBuffer();
//		try {
			Scanner dataIn = new Scanner(f.getInputStream());
			boolean first = true;
			while (dataIn.hasNext()) {
				
				String nextLine = dataIn.nextLine();
				if (!first)
					sb.append("\n");
//				if (nextLine == null)
//					break;
				// System.out.println("new line" + nextLine);
				// sb.append(nextLine+'\n');
				Common.append(sb, nextLine);
				first = false;
//				sb.append("\n");
			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return sb.toString();
	}
	static public List<String> toList(File f) {
//		List<String> retVal = new ArrayList();
		if (f == null) {
			// System.out.println();
			return new ArrayList();
		}
		StringBuffer sb = new StringBuffer();
		try {
			return toList(new FileInputStream(f));

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}
	
	static public List<String> toList(FileProxy f) {
//		List<String> retVal = new ArrayList();
		if (f == null) {
			// System.out.println();
			return new ArrayList();
		}
		StringBuffer sb = new StringBuffer();
//		try {
			return toList(f.getInputStream());

			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return new ArrayList();
	}
	
//	static public List<String> toList(FileProxy f) {
//		List<String> retVal = new ArrayList();
//		if (f == null) {
//			// System.out.println();
//			return retVal;
//		}
//		StringBuffer sb = new StringBuffer();
////		try {
//			Scanner dataIn = new Scanner(f.getInputStream());
//			boolean first = true;
//			while (dataIn.hasNext()) {
//				
//				String nextLine = dataIn.nextLine();
//				retVal.add(nextLine);
////				if (nextLine == null)
////					break;
//				// System.out.println("new line" + nextLine);
//				// sb.append(nextLine+'\n');
////				sb.append("\n");
//			}
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//		return retVal;
//	}
	static public List<String> toList(InputStream input) {
		List<String> retVal = new ArrayList();

//		try {
			Scanner dataIn = new Scanner(input);
			boolean first = true;
			while (dataIn.hasNext()) {
				
				String nextLine = dataIn.nextLine();
				retVal.add(nextLine);
//				if (nextLine == null)
//					break;
				// System.out.println("new line" + nextLine);
				// sb.append(nextLine+'\n');
//				sb.append("\n");
			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return retVal;
		
	}
	


}

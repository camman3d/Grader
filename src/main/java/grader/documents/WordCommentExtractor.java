package grader.documents;

import grader.TestExtractZippedFile;
import grader.project.source.ClassesTextManager;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import util.trace.Tracer;
// shows how one can extract word comments from a word documentm. which is essentially a zipped folder
public class WordCommentExtractor {
	static String wordFile = ClassesTextManager.DEFAULT_SOURCES_FILE_NAME;
	public static final String ZIPPED_FOLDER = wordFile;
	public static final String ZIP_FILE = "word/comments.xml";
	public static final int ESTIMATED_ZIP_FILE_SIZE = 100;
	public static final String START_COMMENT = "<w:t>";
	public static final String END_COMMENT = "</w:t>";


	public static void main (String[] args) {
		BufferedReader bufferredReader = TestExtractZippedFile.toBufferedReader(ZIPPED_FOLDER, ZIP_FILE);
		StringBuffer stringBuffer = new StringBuffer(ESTIMATED_ZIP_FILE_SIZE);
		addToStringBuffer(stringBuffer, bufferredReader);
		System.out.println(stringBuffer);
		List<String> comments = toComments(stringBuffer);
		System.out.println(comments);
	}
	
	public static void addToStringBuffer(StringBuffer aStringBuffer, BufferedReader aBufferdReader) {
		String inputLine;
		while (true) {
			try {
				inputLine = aBufferdReader.readLine();
				if (inputLine == null) break;
			} catch (Exception e) {
				break;
			}
			aStringBuffer.append(inputLine +"\n");
		}
	}
	public static List<String> toComments(StringBuffer aStringBuffer) {
		List<String> retVal = new ArrayList();
//		String[] matches = aStringBuffer.toString().split(START_COMMENT + ".*" + END_COMMENT);
		int fromIndex = 0;
		int endIndex = 0;
		while (true) {
			fromIndex = aStringBuffer.indexOf(START_COMMENT, fromIndex);
			if (fromIndex < 0)
				break;
			endIndex = aStringBuffer.indexOf(END_COMMENT, fromIndex + START_COMMENT.length());
			if (endIndex < 0) {
				Tracer.error("Did not get end comment " + END_COMMENT + " after start comment " + START_COMMENT);
				break;
			}
			String comment = aStringBuffer.substring(fromIndex + START_COMMENT.length(), endIndex);
			retVal.add(comment);
			fromIndex = endIndex + 1;			
		}
		return retVal;		
	}


	

}

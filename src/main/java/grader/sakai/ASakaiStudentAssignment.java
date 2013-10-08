package grader.sakai;

import grader.file.FileProxy;
import grader.file.FileProxyUtils;
import util.misc.Common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ASakaiStudentAssignment implements StudentAssignment {
	public static String SUBMISSION_LOCAL_NAME = "Submission attachment(s)";
	public static String FEEDBACK_LOCAL_NAME = "Feedback Attachment(s)";
	public static String COMMENTS_LOCAL_NAME = "comments.txt";
	public static String TIMESTAMP_LOCAL_NAME = "timestamp.txt"; 
	FileProxy submissionFolder, feedbackFolder, commentsFile, timeStampFile;
	Date date;
	String timeStamp;
	String studentDescription;
	String name, onyen;
	boolean submitted;
	List<String> documents = new ArrayList();
	String commentsFileName;
	
	
	
	FileProxy studentFolder;
	public ASakaiStudentAssignment(String aStudentDescription, FileProxy aFileProxy) {
		try {
//		name = Common.absoluteNameToLocalName(aFileProxy.getAbsoluteName());
		studentDescription = aStudentDescription;
		int parenIndex = aStudentDescription.indexOf("(");
		name = aStudentDescription.substring(0, parenIndex);
		onyen = aStudentDescription.substring(parenIndex + 1, studentDescription.length() -1);
		studentFolder = aFileProxy;
		submissionFolder = aFileProxy.getFileEntryFromLocalName(SUBMISSION_LOCAL_NAME);
		feedbackFolder = aFileProxy.getFileEntryFromLocalName(FEEDBACK_LOCAL_NAME);
		commentsFile = aFileProxy.getFileEntryFromLocalName(COMMENTS_LOCAL_NAME);
		commentsFileName = commentsFile.getAbsoluteName();
		timeStampFile = aFileProxy.getFileEntryFromLocalName(TIMESTAMP_LOCAL_NAME);
		timeStamp = FileProxyUtils.toText(timeStampFile);
		if (timeStamp != null) {
			date = Common.toDate(timeStamp);
		}
		submitted =  timeStamp != null && date != null;
		findDocuments();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	void findDocuments() {
		Set<String> entryNames = studentFolder.getDescendentEntryNames(feedbackFolder);
		for (String entryName:entryNames) {
			if (Common.isDocumentName(entryName))
			documents.add(entryName);
			
		}
	}
	
	public boolean isSubmitted() {
		return submitted;
	}
	
	public String getStudentName() {
		return name;
	}	
	public String getOnyen() {
		return onyen;
	}
	public Date getDate() {
		return date;
	}
	public String getTimeStamp() {
		return timeStamp;		
	}
	public FileProxy getSubmissionFolder() {
		return submissionFolder;
	}
	public FileProxy getFeedbackFolder() {
		return feedbackFolder;
	}
	public FileProxy getCommentsFile() {
		return commentsFile;
	}
	@Override
	public String getCommentsFileName() {
		return commentsFileName;
	}
	public FileProxy getTimeStampFile() {
		return timeStampFile;
	}
	public FileProxy getStudentFolder() {
		return studentFolder;
	}
	
	public List<String> getDocuments() {
		return documents;
	}

	@Override
	public String getStudentDescription() {
		// TODO Auto-generated method stub
		return studentDescription;
	}
	

}

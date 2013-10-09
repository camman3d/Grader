package grader.sakai;

import grader.file.FileProxy;

import java.util.Date;
import java.util.List;

public interface StudentAssignment {

    public FileProxy getSubmissionFolder();

    public FileProxy getFeedbackFolder();

    public FileProxy getCommentsFile();

    public FileProxy getTimeStampFile();

    public FileProxy getStudentFolder();

    public Date getDate();

    public String getTimeStamp();

    public String getStudentName();

    public String getStudentDescription();

    public String getOnyen();

    public boolean isSubmitted();

    public List<String> getDocuments();

    String getCommentsFileName();

}

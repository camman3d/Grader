package grader.assignment;

import grader.file.FileProxy;
import grader.file.RootFolderProxy;

import java.util.List;
import java.util.Set;

public interface AssignmentDataFolder extends RootFolderProxy {
    public Set<String> getInputFiles();

    public List<String> getStudentIDs();

    public FileProxy getFeatureGradeFile();

    public String getIdFileName();

    public void setIdFileName(String idFileName);

    public String getGradedIdFileName();

    public void setGradedIdFileName(String gradedIdFileName);

    public String getSkippedIdFileName();

    public void setSkippedIdFileName(String skippedIdFileName);

    public String getLogFileName();

    public void setLogFileName(String logFileName);

    public String getInputFolderName();

    public void setInputFolderName(String inputFolderName);

    public String getFeatureGradeFileName();

    public void setFeatureGradeFileName(String featureGradeFileName);

    public String getIdText();

    public void setIdText(String idText);

    public FileProxy getFinalGradeFile();

    public void setFinalGradeFile(FileProxy finalGradeFile);

    public void setInputFiles(Set<String> inputFiles);

    public void setStudentIDs(List<String> studentIDs);

    public void setFeatureGradeFile(FileProxy featureGradeFile);


}

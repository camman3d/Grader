package grader.sakai.project;

import grader.project.Project;
import grader.sakai.StudentAssignment;

public interface SakaiProject extends Project {
    public StudentAssignment getStudentAssignment();

    public void displaySource(SakaiProjectDatabase aSakaiProjectDatabase);


}

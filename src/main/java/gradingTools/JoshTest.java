package gradingTools;

import framework.wrappers.NonNestedBulkAssignmentFolder;
import grader.sakai.ASakaiBulkAssignmentFolder;
import grader.sakai.BulkAssignmentFolder;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/8/13
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class JoshTest {
    public static void main(String[] args) {
        BulkAssignmentFolder folder = new ASakaiBulkAssignmentFolder("/Users/josh/Downloads/Assignment 2 All", "Assignment2");

        System.out.println("Before\n------");
        System.out.println("Assignment folder: " + folder.getAssignmentFolder().getAbsoluteName());
        System.out.println("Student folders: " + folder.getStudentFolderNames());

        BulkAssignmentFolder folder2 = new NonNestedBulkAssignmentFolder();

        System.out.println("\nAfter\n-----");
        System.out.println("Assignment folder: " + folder2.getAssignmentFolder().getAbsoluteName());
        System.out.println("Student folders: " + folder2.getStudentFolderNames());

    }
}

package framework.navigation;

import java.io.File;
import java.util.List;

/**
 * Based on {@link grader.sakai.BulkAssignmentFolder}
 */
public interface BulkDownloadFolder {

    /**
     * @return The grades.csv file
     */
    public File getGrades();

    /**
     * @return The students' folder wrapped in a {@link StudentFolder}
     */
    public List<StudentFolder> getStudentFolders();

    /**
     * @param start The onyen to start with
     * @param end The onyen to end with
     * @return The students' folder wrapped in a {@link StudentFolder}
     */
    public List<StudentFolder> getStudentFolders(String start, String end);

    /**
     * Finds a {@link StudentFolder} based on the student's onyen
     *
     * @param onyen The student's Onyen
     * @return The {@link StudentFolder}
     */
    public StudentFolder getStudentFolder(String onyen);

    /**
     * @return The download root
     */
    public File getFolder();
}

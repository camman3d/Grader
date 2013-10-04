package framework.navigation;

import java.io.File;
import java.util.List;

/**
 * Like BulkAssignmentFolder
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
     * Finds a {@link StudentFolder} based on the student's onyen
     *
     * @param onyen The student's Onyen
     * @return The {@link StudentFolder}
     */
    public StudentFolder getStudentFolder(String onyen);
}

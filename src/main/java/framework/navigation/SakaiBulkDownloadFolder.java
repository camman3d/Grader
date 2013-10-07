package framework.navigation;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/3/13
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class SakaiBulkDownloadFolder implements BulkDownloadFolder {

    private File folder;
    private File grades;

    public SakaiBulkDownloadFolder(String path) throws NotValidDownloadFolderException {
        folder = new File(path);

        // Find the grades.csv file. This is how we check that this is a valid location
        File[] foundFiles = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().equals("grades.csv");
            }
        });
        if (foundFiles.length == 0)
            throw new NotValidDownloadFolderException();
        grades = foundFiles[0];
    }

    @Override
    public File getGrades() {
        return grades;
    }

    @Override
    public List<StudentFolder> getStudentFolders() {
        File[] folders = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        List<StudentFolder> studentFolders = new ArrayList<StudentFolder>();
        for (File studentFolder : folders)
            studentFolders.add(new SakaiStudentFolder(studentFolder));
        return studentFolders;
    }

    @Override
    public List<StudentFolder> getStudentFolders(String start, String end) {
        List<StudentFolder> filteredFolders = new ArrayList<StudentFolder>();
        boolean collect = false;
        for (StudentFolder studentFolder : getStudentFolders()) {
            if (studentFolder.getOnyen().equals(start))
                collect = true;
            if (collect)
                filteredFolders.add(studentFolder);
            if (studentFolder.getOnyen().equals(end))
                collect = false;
        }
        return filteredFolders;
    }

    @Override
    public StudentFolder getStudentFolder(final String onyen) {
        File studentFolder = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("(" + onyen + ")");
            }
        })[0];
        return new SakaiStudentFolder(studentFolder);
    }

    @Override
    public File getFolder() {
        return folder;
    }
}

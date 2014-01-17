package wrappers.grader.sakai;

import framework.utils.GradingManifest;
import framework.utils.GradingSettings;
import framework.utils.GradingEnvironment;
import wrappers.grader.file.SimplifiedFileProxy;
import grader.file.FileProxy;
import grader.sakai.BulkAssignmentFolder;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

/**
 * Similar to {@link grader.sakai.ASakaiBulkAssignmentFolder} but it doesn't assume it is nested within another folder
 */
public class NonNestedBulkAssignmentFolder implements BulkAssignmentFolder {

    private SimplifiedFileProxy rootProxy;
    private FileProxy spreadsheet;

    public NonNestedBulkAssignmentFolder() {
        String path = GradingManifest.getActiveManifest().getDownloadPath();
        rootProxy = new SimplifiedFileProxy(new File(path));
        spreadsheet = new SimplifiedFileProxy(new File(rootProxy.getFile(), "grades.csv"));
    }

    @Override
    public FileProxy getAssignmentFolder() {
        return rootProxy;
    }

    @Override
    public Set<String> getStudentFolderNames() {
        File[] folders = rootProxy.getFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        Set<String> names = new HashSet<String>();
        for (File file : folders)
            names.add(file.getAbsolutePath());
        return names;
    }

    @Override
    public FileProxy getStudentFolder(String aName) {
        return new SimplifiedFileProxy(new File(aName));
    }

    @Override
    public FileProxy getSpreadsheet() {
        return spreadsheet;
    }

    @Override
    public String getAssignmentName() {
        return GradingManifest.getActiveManifest().getProjectName().toLowerCase();
    }

    @Override
    public String getMixedCaseAssignmentName() {
        return GradingManifest.getActiveManifest().getProjectName();
    }
}

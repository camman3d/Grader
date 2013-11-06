package framework.navigation;

import framework.project.StandardProject;
import tools.DirectoryUtils;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import scala.Option;
import util.misc.Common;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * A Sakai-structured student folder.
 */
public class SakaiStudentFolder implements StudentFolder {

    private File folder;

    public SakaiStudentFolder(File folder) {
        this.folder = folder;
    }

    /**
     * @return The root folder
     */
    @Override
    public File getFolder() {
        return folder;
    }

    /**
     * @return The feedback folder
     */
    @Override
    public File getFeedbackFolder() {
        return new File(folder, "Feedback Attachment(s)");
    }

    /**
     * @return The student's ID, which is "Last Name, First Name(Onyen)"
     */
    @Override
    public String getUserId() {
        return folder.getName();
    }

    /**
     * @return The student's Onyen
     */
    @Override
    public String getOnyen() {
        String id = getUserId();
        return id.substring(id.indexOf('(') + 1, id.indexOf(')'));
    }

    /**
     * Looks for and returns the project that the student submitted. This is wrapped in an {@link Option} in case it
     * doesn't exist.
     *
     * @param name The preferred name of the project. This will be used later when finding the main method in the code
     * @return The wrapped project
     */
    @Override
    public Option<StandardProject> getProject(String name) {
        // First, open up the submission folder.
        File submissionFolder = new File(folder, "Submission attachment(s)");

        // Look for a folder, taking the first one found.
        Option<File> projectFolder = DirectoryUtils.find(submissionFolder, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() && !pathname.getName().equals("__MACOSX");
            }
        });
        if (projectFolder.isDefined())
            try {
                return Option.apply(new StandardProject(projectFolder.get(), name));
            } catch (Exception e) {
                return Option.empty();
            }


        // Couldn't find it, so look for a .zip file.
        Option<File> zipFile = DirectoryUtils.find(submissionFolder, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".zip");
            }
        });
        if (zipFile.isEmpty())
            return Option.empty();

        // Extract the zip to look for the folder
        try {
            ZipFile zip = new ZipFile(zipFile.get());
            zip.extractAll(submissionFolder.getAbsolutePath());

            // Look for a folder, taking the first one found.
            Option<File> projectFolder2 = DirectoryUtils.find(submissionFolder, new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });
            if (projectFolder2.isDefined())
                try {
                    return Option.apply(new StandardProject(projectFolder2.get(), name));
                } catch (Exception e) {
                    return Option.empty();
                }
            return Option.empty();
        } catch (ZipException e) {
            return Option.empty();
        }
    }

    /**
     * Figures out the time the student submitted things. This is wrapped in an {@link Option} in case nothing was
     * submitted.
     *
     * @return The time as a {@link DateTime} for better time manipulation.
     */
    @Override
    public Option<DateTime> getTimestamp() {
        File timestampFile = new File(folder, "timestamp.txt");
        if (timestampFile.exists()) {
            try {
                String timestampText = FileUtils.readFileToString(timestampFile);
                return Option.apply(new DateTime(Common.toDate(timestampText)));
            } catch (IOException e) {
                return Option.empty();
            }
        } else
            return Option.empty();
    }
}

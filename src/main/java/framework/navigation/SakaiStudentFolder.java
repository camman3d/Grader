package framework.navigation;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import framework.project.StandardProject;
import scala.Option;
import util.misc.Common;
import framework.utils.DirectoryUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Date;

/**
 * A Sakai-structured student folder.
 */
public class SakaiStudentFolder implements StudentFolder {

    private File folder;

    public SakaiStudentFolder(File folder) {
        this.folder = folder;
    }

    @Override
    public File getFolder() {
        return folder;
    }

    @Override
    public Option<DateTime> getSubmissionTime() {
        File timestampFile = new File(folder, "timestamp.txt");
        if (!timestampFile.exists())
            return Option.empty();
        try {
            Date date = Common.toDate(FileUtils.readFileToString(timestampFile));
            return Option.apply(new DateTime(date));
        } catch (IOException e) {
            return Option.empty();
        }
    }

    @Override
    public File getFeedbackFolder() {
        return new File(folder, "Feedback Attachment(s)");
    }

    @Override
    public String getUserId() {
        return folder.getName();
    }

    @Override
    public String getOnyen() {
        String id = getUserId();
        return id.substring(id.indexOf('(') + 1, id.indexOf(')'));
    }

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

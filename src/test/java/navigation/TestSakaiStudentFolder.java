package navigation;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import project.StandardProject;
import scala.Option;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link SakaiStudentFolder}
 */
public class TestSakaiStudentFolder {

    // Values for testing
    private String onTimeStudentFolder = "/Users/josh/Downloads/Assignment 2 All/Alakkat, Grishma(alakkat)";
    private String noSubmissionStudentFolder = "/Users/josh/Downloads/Assignment 2 All/Monson, Joshua(jmonson)";
    private String userId = "Alakkat, Grishma(alakkat)";
    private String onyen = "alakkat";
    private String assignmentName = "Assignment2";
    private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    private DateTime early = formatter.parseDateTime("04/09/2013 23:55:00");
    private DateTime late = formatter.parseDateTime("06/09/2013 23:55:00");

    StudentFolder folder = new SakaiStudentFolder(new File(onTimeStudentFolder));

    @Test
    public void testGetSubmissionTime() {
        Option<DateTime> time = folder.getSubmissionTime();
        assertTrue("A timestamp should have been found", time.isDefined());
        assertTrue("Incorrect timestamp value", time.get().isAfter(early) && time.get().isBefore(late));
    }

    @Test
    public void testGetFeedbackFolder() {
        File feedbackFolder = folder.getFeedbackFolder();
        assertTrue("Folder should exist", feedbackFolder.exists());
        assertTrue("Should be a directory", feedbackFolder.isDirectory());
    }

    @Test
    public void testGetUserId() {
        assertEquals("Should get correct user ID", userId, folder.getUserId());
    }

    @Test
    public void testGetOnyen() {
        assertEquals("Should get correct Onyen", onyen, folder.getOnyen());
    }

    @Test
    public void testGetProject() {
        Option<StandardProject> project = folder.getProject(assignmentName);
        assertTrue("Project should be found", project.isDefined());
    }

    @Test
    public void testGetNonExistentProject() {
        Option<StandardProject> project = new SakaiStudentFolder(new File(noSubmissionStudentFolder)).getProject(assignmentName);
        assertTrue("Project should not be found", project.isEmpty());
    }

}

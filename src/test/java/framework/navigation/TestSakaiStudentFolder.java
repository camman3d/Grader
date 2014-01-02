package framework.navigation;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import framework.project.StandardProject;
import scala.Option;
import tools.TestConfig;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link SakaiStudentFolder}
 */
public class TestSakaiStudentFolder {
/*
    // Values for testing
//    private String onTimeStudentFolder = "/Users/josh/Downloads/Assignment 2 All/Alakkat, Grishma(alakkat)";
//    private String noSubmissionStudentFolder = "/Users/josh/Downloads/Assignment 2 All/Monson, Joshua(jmonson)";
//    private String userId = "Alakkat, Grishma(alakkat)";
//    private String onyen = "alakkat";
//    private String assignmentName = "Assignment2";
//    private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
//    private DateTime early = formatter.parseDateTime("04/09/2013 23:55:00");
//    private DateTime late = formatter.parseDateTime("06/09/2013 23:55:00");

//    StudentFolder folder = new SakaiStudentFolder(new File(onTimeStudentFolder));

    private StudentFolder folder1, folder2;
    String userId, onyen, assignmentName;

    @Before
    public void setUp() throws Exception {

        // Load the two different projects
        String example1Path = TestConfig.getConfig().getString("test.exampleSakai.example1.path");
        String example2Path = TestConfig.getConfig().getString("test.exampleSakai.example2.path");
        folder1 = new SakaiStudentFolder(new File(example1Path));
        folder2 = new SakaiStudentFolder(new File(example2Path));

        // Load more info about project 1
        userId = TestConfig.getConfig().getString("test.exampleSakai.example1.userId");
        onyen = TestConfig.getConfig().getString("test.exampleSakai.example1.onyen");

        // Load general information
        assignmentName = TestConfig.getConfig().getString("test.exampleSakai.projectName");
    }

    @Test
    public void testGetSubmissionTime() {
        Option<DateTime> time = folder1.getTimestamp();
        assertTrue("A timestamp should have been found", time.isDefined());
        time = folder2.getTimestamp();
        assertTrue("Folder 2 has no timestamp", time.isEmpty());
    }

    @Test
    public void testGetFeedbackFolder() {
        File feedbackFolder = folder1.getFeedbackFolder();
        assertTrue("Folder should exist", feedbackFolder.exists());
        assertTrue("Should be a directory", feedbackFolder.isDirectory());
    }

    @Test
    public void testGetUserId() {
        assertEquals("Should get correct user ID", userId, folder1.getUserId());
    }

    @Test
    public void testGetOnyen() {
        assertEquals("Should get correct Onyen", onyen, folder1.getOnyen());
    }

    @Test
    public void testGetProject() {
        Option<StandardProject> project = folder1.getProject(assignmentName);
        assertTrue("Project should be found", project.isDefined());
    }

    @Test
    public void testGetNonExistentProject() {
        Option<StandardProject> project = folder2.getProject(assignmentName);
        assertTrue("Project should not be found", project.isEmpty());
    }
*/
}

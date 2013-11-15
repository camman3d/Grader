package framework.navigation;

import org.junit.Before;
import org.junit.Test;
import tools.TestConfig;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for {@link framework.navigation.BulkDownloadFolder}
 */
public class TestBulkDownloadFolder {

    private String bulkFolder;
    private String onyen;

    @Before
    public void setUp() throws Exception {
        bulkFolder = TestConfig.getConfig().getString("test.exampleSakai.path");
        onyen = TestConfig.getConfig().getString("test.exampleSakai.example1.onyen");
    }

    @Test
    public void testValidCreation() {
        try {
            new SakaiBulkDownloadFolder(bulkFolder);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue("Couldn't create", false);
        }
    }

    @Test
    public void testInvalidCreation() {
        try {
            new SakaiBulkDownloadFolder(".");
            assertTrue("Didn't check for valid path", false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetGrades() throws NotValidDownloadFolderException {
        BulkDownloadFolder downloadFolder = new SakaiBulkDownloadFolder(bulkFolder);
        File grades = downloadFolder.getGrades();
        assertEquals("grades.csv", grades.getName());
    }

    @Test
    public void testGetStudentFolders() throws NotValidDownloadFolderException {
        BulkDownloadFolder downloadFolder = new SakaiBulkDownloadFolder(bulkFolder);
        List<StudentFolder> folders = downloadFolder.getStudentFolders();
        assertFalse("No student folders found", folders.isEmpty());
    }

    @Test
    public void testGetStudentFolder() throws NotValidDownloadFolderException {
        BulkDownloadFolder downloadFolder = new SakaiBulkDownloadFolder(bulkFolder);
        StudentFolder folder = downloadFolder.getStudentFolder(onyen);
        assertFalse("No student folder found", folder == null);
    }
}

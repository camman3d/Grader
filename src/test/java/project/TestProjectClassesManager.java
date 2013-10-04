package project;

import org.junit.Test;
import scala.Option;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link TestProjectClassesManager}
 */
public class TestProjectClassesManager {

    private String validBuildLocation = "/Users/josh/Downloads/Assignment 4/Weatherly, Nathan(nweather)/Submission attachment(s)/NathanWeatherly_Assignment4/NathanWeatherly_Assignment4/bin";
    private String validSrcLocation = "/Users/josh/Downloads/Assignment 4/Weatherly, Nathan(nweather)/Submission attachment(s)/NathanWeatherly_Assignment4/NathanWeatherly_Assignment4/src";
    private String invalidLocation = "/tmp";
    private String simpleClassName = "ABeanScannertype";
    private String canonicalClassName = "main.Assignment4";
    private String invalidClassName = "doesntexist";
    private String validTag = "scanner bean";
    private String invalidTag = "Foo bar";


    @Test
    public void testCreation() {
        try {
            new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
            assertTrue(true);
        } catch (Exception e) {
            assertTrue("Failed to load classes.", false);
        }
    }

    @Test
    public void testFailedCreation() {
        try {
            new ProjectClassesManager(new File(invalidLocation), new File(invalidLocation));
            assertTrue("Creation should fail", false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetClassLoader() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        assertFalse("Class loader should exist", classesManager.getClassLoader() == null);
    }

    @Test
    public void testFindByClassNameSimple() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        Option<ClassDescription> description = classesManager.findByClassName(simpleClassName);
        assertTrue("Class should exist", description.isDefined());
    }

    @Test
    public void testFindByClassNameCanonical() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        Option<ClassDescription> description = classesManager.findByClassName(canonicalClassName);
        assertTrue("Class should exist", description.isDefined());
    }

    @Test
    public void testFindByClassNameInvalid() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        Option<ClassDescription> description = classesManager.findByClassName(invalidClassName);
        assertTrue("Class should not exist", description.isEmpty());
    }

    @Test
    public void testFindByTag() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        Option<ClassDescription> description = classesManager.findByTag(validTag);
        assertTrue("Class should exist", description.isDefined());
    }

    @Test
    public void testFindByTagInvalid() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        Option<ClassDescription> description = classesManager.findByTag(invalidTag);
        assertTrue("Class should not exist", description.isEmpty());
    }

    @Test
    public void testGetClassDescriptions() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        assertFalse("Class descriptions should not be empty", classesManager.getClassDescriptions().isEmpty());
    }

}

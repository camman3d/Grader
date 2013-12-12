package framework.project;

import org.junit.Before;
import org.junit.Test;
import scala.Option;
import tools.TestConfig;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link TestProjectClassesManager}
 */
public class TestProjectClassesManager {

    private String validBuildLocation;
    private String validSrcLocation;
    private String simpleClassName;
    private String canonicalClassName;
    private String invalidName;
    private String validTag;

    @Before
    public void setUp() throws Exception {
        validBuildLocation = TestConfig.getConfig().getString("test.exampleSakai.example1.build");
        validSrcLocation = TestConfig.getConfig().getString("test.exampleSakai.example1.source");
        validTag = TestConfig.getConfig().getString("test.exampleSakai.example1.tag");
        simpleClassName = TestConfig.getConfig().getString("test.exampleSakai.example1.simpleName");
        canonicalClassName = TestConfig.getConfig().getString("test.exampleSakai.example1.canonicalName");
        invalidName = "__doesntexist__";
    }

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
            String invalidLocation = "/";
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
        Option<ClassDescription> description = classesManager.findByClassName(invalidName);
        assertTrue("Class should not exist", description.isEmpty());
    }

    @Test
    public void testFindByTag() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        Set<ClassDescription> description = classesManager.findByTag(validTag);
        assertFalse("Class should exist", description.isEmpty());
    }

    @Test
    public void testFindByTagInvalid() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        Set<ClassDescription> description = classesManager.findByTag(invalidName);
        assertTrue("Class should not exist", description.isEmpty());
    }

    @Test
    public void testGetClassDescriptions() throws IOException, ClassNotFoundException {
        ClassesManager classesManager = new ProjectClassesManager(new File(validBuildLocation), new File(validSrcLocation));
        assertFalse("Class descriptions should not be empty", classesManager.getClassDescriptions().isEmpty());
    }

}

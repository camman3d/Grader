package framework.project;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 6:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestBasicClassDescription {

    private File sourceFile;
    private Class _class;
    ClassDescription classDescription;

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        _class = classLoader.loadClass("testObjects.AnnotatedClass");
        sourceFile = new File("./src/test/java/testObjects/AnnotatedClass.java");
        classDescription = new BasicClassDescription(_class, sourceFile);
    }

    @Test
    public void testGetJavaClass() {
        assertEquals("Returns correct Java class", _class, classDescription.getJavaClass());
    }

    @Test
    public void testGetSource() {
        assertEquals("Returns correct source file", sourceFile, classDescription.getSource());
    }

    @Test
    public void testGetTags() {
        assertEquals("Returns correct tags", "Annotated", classDescription.getTags()[0]);
    }

    @Test
    public void testGetStructurePatternName() {
        assertEquals("Returns correct structure pattern", "My Pattern", classDescription.getStructurePatternName());
    }

    @Test
    public void testGetPropertyNames() {
        String[] names = new String[]{"One", "Two"};
        String[] retrievedNames = classDescription.getPropertyNames();
        for (int i = 0; i < retrievedNames.length; i++)
            assertEquals("Returns correct property names", names[i], retrievedNames[i]);
    }

    @Test
    public void testGetEditablePropertyNames() {
        String[] names = new String[]{"One", "Two", "Three"};
        String[] retrievedNames = classDescription.getEditablePropertyNames();
        for (int i = 0; i < retrievedNames.length; i++)
            assertEquals("Returns correct editable property names", names[i], retrievedNames[i]);
    }
}

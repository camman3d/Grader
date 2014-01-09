package gradingTools.assignment9.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/6/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaintListenerPaintMethodTestCase extends BasicTestCase {

    public PaintListenerPaintMethodTestCase() {
        super("Paint Listener has a paint(Graphics2D) method");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // Make sure we can get the class description
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Set<ClassDescription> classDescriptions = project.getClassesManager().get().findByTag("Paint Listener");
        if (classDescriptions.isEmpty())
            return fail("No class tagged \"Paint Listener\"", autoGrade);
        ClassDescription classDescription = new ArrayList<ClassDescription>(classDescriptions).get(0);

        try {
            classDescription.getJavaClass().getMethod("paint", Graphics2D.class);
            return pass(autoGrade);
        } catch (NoSuchMethodException e) {
            return fail("No paint(Graphic2D) method found.", autoGrade);
        }
    }
}

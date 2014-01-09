package gradingTools.assignment8.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import tools.classFinder.ManualClassFinder;
import tools.classFinder.RootTagFinder;
import scala.Option;

import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/29/13
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleViewListenerTestCase extends BasicTestCase {

    public ConsoleViewListenerTestCase() {
        super("Console view implements PropertyChangeListener test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // Make sure we can get the class description
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Option<ClassDescription> classDescription = new RootTagFinder(project).findClass("Console Scene View");
        if (classDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            classDescription = ManualClassFinder.find(project, "Console Scene View");
        }

        if (PropertyChangeListener.class.isAssignableFrom(classDescription.get().getJavaClass()))
            return pass(autoGrade);
        else
            return fail("Console scene view should implement PropertyChangeListener", autoGrade);
    }
}

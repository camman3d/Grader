package gradingTools.assignment10.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;
import tools.classFinder2.ClassFinder;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/11/13
 * Time: 10:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandImplementsRunnableTestCase extends BasicTestCase {

    private String tag;

    public CommandImplementsRunnableTestCase(String tag) {
        super("\"" + tag + "\" command object test case");
        this.tag = tag;
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Find the class
        String notes = "";
        double score = 1;
        if (project.getClassesManager().get().findByTag(tag).isEmpty()) {
            score = 0.5;
            notes = "Class was not tagged.";
        }
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByTag(tag, autoGrade);
        if (classDescription.isEmpty())
            return fail("No class tagged: " + tag);

        // Make sure that it implements Runnable
        if (Runnable.class.isAssignableFrom(classDescription.get().getJavaClass()))
            return partialPass(score, notes);
        notes += (notes.isEmpty() ? "" : "\n") + "Class does not implement Runnbale";
        return partialPass(0, notes);
    }
}

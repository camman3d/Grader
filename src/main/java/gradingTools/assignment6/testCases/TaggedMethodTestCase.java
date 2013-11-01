package gradingTools.assignment6.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import util.annotations.Tags;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaggedMethodTestCase extends BasicTestCase {

    private String tag;

    public TaggedMethodTestCase(String tag, String name) {
        super(name);
        this.tag = tag;
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Set<ClassDescription> classDescriptions = project.getClassesManager().get().getClassDescriptions();
        for (ClassDescription description : classDescriptions) {
            Method[] methods = description.getJavaClass().getMethods();
            for (Method method : methods) {
                try {
                    String[] tags = method.getAnnotation(Tags.class).value();
                    for (String t : tags)
                        if (tag.equalsIgnoreCase(tag))
                            return pass();
                } catch (Exception e) {}
            }
        }
        return fail("No method is tagged: " + tag);
    }
}

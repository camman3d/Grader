package gradingTools.comp410.a1_vipQueue.tests;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import tools.CodeTools;
import tools.classFinder2.FieldFinder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/30/14
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class NoJavaCollectionsTestCase extends BasicTestCase {

    public NoJavaCollectionsTestCase() {
        super("No java collections test case");
    }

    // Just look for the basic interfaces
    private Set<Class<?>> forbiddenClasses = new HashSet<Class<?>>() {{
        add(java.util.List.class);
        add(java.util.Set.class);
        add(java.util.Queue.class);
        add(java.util.Map.class);
    }};

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        Set<ClassDescription> classDescriptions = project.getClassesManager().get().getClassDescriptions();
        for (ClassDescription description : classDescriptions) {

                // Look for usage of forbidden collections as a field
                Field[] fields = description.getJavaClass().getDeclaredFields();
                if (fields != null)
                    for (Field f : fields) {
//                        f.setAccessible(true);
                        for (Class<?> forbidden : forbiddenClasses) {
                            if (forbidden.isAssignableFrom(f.getType()))
                                return fail("Forbidden Java class " + forbidden.getSimpleName() + " used in " + description.getSource().getName());
                        }
                    }

        }
        return pass();
    }
}

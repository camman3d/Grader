package gradingTools.assignment9.testCases;

import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import com.github.antlrjavaparser.api.body.ConstructorDeclaration;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import tools.CompilationNavigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/6/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaintListenerListenersTestCase extends BasicTestCase {

    public PaintListenerListenersTestCase() {
        super("Paint listener views are registered as listeners test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // Make sure we can get the class description
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Set<ClassDescription> classDescriptions = project.getClassesManager().get().findByTag("Paint Listener");
        if (classDescriptions.isEmpty())
            return fail("No class tagged \"Paint Listener\"");
        ClassDescription classDescription = new ArrayList<ClassDescription>(classDescriptions).get(0);

        // Get the views
        Class<?> paintListener = classDescription.getJavaClass();
        List<ClassDescription> views = new ArrayList<ClassDescription>();
        for (ClassDescription description : project.getClassesManager().get().getClassDescriptions()) {
            if (!description.getJavaClass().isInterface() && paintListener.isAssignableFrom(description.getJavaClass())) {
                views.add(description);
            }
        }

        // Count how many views register themselves as listeners in the constructor
        double listenerCount = 0;
        String notes = "";
        for (ClassDescription view : views) {
            // Get the constructors
            try {
                ClassOrInterfaceDeclaration classDef = view.parse();
                List<ConstructorDeclaration> constructors = CompilationNavigation.getConstructors(classDef);

                // Look for one assignment in any constructor
                boolean found = false;
                for (ConstructorDeclaration constructor : constructors) {
                    String code = constructor.toString();
                    if (code.contains(".addPropertyChangeListener(this)")) {
                        found = true;
                        break;
                    }
                }
                if (found)
                    listenerCount++;
                else
                    notes += "Paint listener view " + view.getJavaClass().getSimpleName() + " doesn't register itself as a listener in its constructor.\n";
            } catch (IOException e) {
                // Don't do anything here.
            }
        }

        double count = views.size();
        return partialPass(listenerCount / count, notes);
    }
}

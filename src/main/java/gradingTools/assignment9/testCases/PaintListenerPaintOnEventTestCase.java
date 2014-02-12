package gradingTools.assignment9.testCases;

import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import com.github.antlrjavaparser.api.body.ConstructorDeclaration;
import com.github.antlrjavaparser.api.body.MethodDeclaration;
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
public class PaintListenerPaintOnEventTestCase extends BasicTestCase {

    public PaintListenerPaintOnEventTestCase() {
        super("Paint listener calls paint on events test case");
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

        // Count how many views call paint or repaint in propertyChange
        double paintCount = 0;
        String notes = "";
        for (ClassDescription view : views) {
            // Get the constructors
            try {
                ClassOrInterfaceDeclaration classDef = view.parse();
                MethodDeclaration method = CompilationNavigation.getMethod(classDef, "propertyChange");
                if (method == null) {
                    notes += "Paint listener view " + view.getJavaClass().getSimpleName() + " doesn't have a propertyChange method.";
                    continue;
                }

                String code = method.toString();

                if (code.contains("paint();"))
                    paintCount++;
                else
                    notes += "Paint listener view " + view.getJavaClass().getSimpleName() + " doesn't call paint() or repaint() when events happen.\n";

            } catch (Exception e) {
                // Don't do anything here.
            }
        }

        double count = views.size();
        return partialPass(paintCount / count, notes);
    }
}

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
public class ScenePainterPaintOnEventTestCase extends BasicTestCase {

    public ScenePainterPaintOnEventTestCase() {
        super("Scene painter paints on events test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // Make sure we can get the class description
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Set<ClassDescription> classDescriptions = project.getClassesManager().get().findByTag("Inheriting Bridge Scene Painter");
        if (classDescriptions.isEmpty())
            return fail("No class tagged \"Inheriting Bridge Scene Painter\"");
        ClassDescription classDescription = new ArrayList<ClassDescription>(classDescriptions).get(0);

        // It should register itself as a listener at least once in the constructor
        // Get the constructor code
        try {
            ClassOrInterfaceDeclaration classDef = classDescription.parse();
            MethodDeclaration method = CompilationNavigation.getMethod(classDef, "propertyChange");
            String code = method.toString();
            if (code.contains("paint();"))
                return pass();
            else
                return fail("The paint() method isn't fired from propertyChange()");
        } catch (IOException e) {
            throw new NotGradableException();
        }
    }
}

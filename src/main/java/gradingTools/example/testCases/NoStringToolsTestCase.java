package gradingTools.example.testCases;

import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import com.github.antlrjavaparser.api.body.MethodDeclaration;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import tools.CodeTools;
import tools.CompilationNavigation;

import java.io.IOException;
import java.util.List;

/**
 * This test case looks for the usage of StringBuilder or StringBuffer in any method.
 */
public class NoStringToolsTestCase extends BasicTestCase {

    public NoStringToolsTestCase() {
        super("No string tool test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // You can also get the code and inspect it. This system uses Antlr to parse Java code
        try {
            for (ClassDescription description : project.getClassesManager().get().getClassDescriptions()) {

                // The CompilationNavigation class offers methods to help navigate and work with the compilation unit
                ClassOrInterfaceDeclaration classDef = CompilationNavigation.getClassDef(description.parse());

                // With antlr parse object, you can call toString at any level to convert it to Java code.
                String code = classDef.toString();

                // Now, we'll do the check
                code = CodeTools.removeComments(code);
                if (code.contains("StringBuilder") || code.contains("StringBuffer"))
                    return fail("StringBuilder/StringBuffer found in class: " + description.getJavaClass().getCanonicalName(), autoGrade);
            }
            return pass(autoGrade);
        } catch (IOException e) {
            throw new NotGradableException();
        }
    }
}

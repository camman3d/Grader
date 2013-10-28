package graders.assignment6.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/8/13
 * Time: 9:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class InterfaceTypeTestCase extends BasicTestCase {
    public InterfaceTypeTestCase(String name) {
        super(name);
    }

    private boolean isStudentClass(List<String> classes, String _class) {
        for (String c : classes)
            if (c.equals(_class))
                return true;
        return false;
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotGradableException {

        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // First, make a list of all the classes in the project
        List<String> classes = new ArrayList<String>();
        Set<ClassDescription> classDescriptions = project.getClassesManager().get().getClassDescriptions();
        for (ClassDescription description : classDescriptions) {
            if (!description.getJavaClass().isInterface())
                classes.add(description.getJavaClass().getSimpleName());
        }

        int nonInterfaceAssignment = 0;
        int assignments = 0;
        String notes = "";

        for (ClassDescription description : classDescriptions) {

            // Find all object assignments
            Field[] fields = description.getJavaClass().getDeclaredFields();
            assignments += fields.length;
            for (Field field : fields) {
                if (field.getType().isInterface() && isStudentClass(classes, field.getType().getSimpleName())) {
                    nonInterfaceAssignment++;
                    notes += (notes.isEmpty() ? "" : "\n") + "Field declaration should be interface type: " + field.toString();
                }
            }

            // Check within methods. We'll need to look at the code here
            try {
                List<String> lines = FileUtils.readLines(description.getSource());
                for (String line : lines) {
                    if (line.matches("^\\s*(\\w+)\\s+\\w+\\s*(=|;).*$")) {
                        assignments++;
                        String type = line.trim().split("\\s+")[0];
                        if (isStudentClass(classes, type)) {
                            nonInterfaceAssignment++;
                            notes += "Object assignment should be interface type: " + line;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        // Give a score based on the numbers
//        double score = 1 - nonInterfaceAssignment / assignments;
        double score = Math.min(nonInterfaceAssignment, 5) / 5.0;
        score = 1 - score;
        return partialPass(score, notes);
    }
}

package gradingTools.assignment11.checkers;

import grader.checkers.ACheckResult;
import grader.checkers.AnAbstractFeatureChecker;
import grader.checkers.CheckResult;
import grader.project.ClassDescription;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdditionalCommandObjectsChecker extends AnAbstractFeatureChecker {

    @Override
    public CheckResult check() {
        CheckResult result = new ACheckResult();

        String[] tags = new String[] {
                "approach command", "pass command", "fail command"
        };
        double validCount = 0;
        for (String tag : tags) {

            // Get the class with the tag
            Set<ClassDescription> descriptions = project.getClassesManager().tagToClassDescriptions(tag);
            if (descriptions.isEmpty())
                result.getLog().add("No class tagged with " + tag);
            else {
                ClassDescription description = new ArrayList<ClassDescription>(descriptions).get(0);
                if (Runnable.class.isAssignableFrom(description.getJavaClass()))
                    validCount++;
                else
                    result.getLog().add("Class is not Runnable: " + tag);
            }
        }

        result.setScore((validCount / 3.0) * feature.getMax());
        return result;
    }
}

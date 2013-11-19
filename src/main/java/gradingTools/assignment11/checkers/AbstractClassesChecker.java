package gradingTools.assignment11.checkers;

import grader.checkers.ACheckResult;
import grader.checkers.AnAbstractFeatureChecker;
import grader.checkers.CheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractClassesChecker extends AnAbstractFeatureChecker {
    @Override
    public CheckResult check() {
        CheckResult result = new ACheckResult();
        result.setScore(feature.getMax() / 2);
        System.out.println(project.getClassesManager().getClassDescriptions().size());
//        project.getClassesManager().tagToClassDescriptions("Locatable");

        return result;
    }
}

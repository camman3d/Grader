package framework.wrappers.framework.grading.testing;

import framework.grading.testing.*;
import framework.project.Project;
import framework.wrappers.framework.project.ProjectWrapper;
import framework.wrappers.grader.sakai.project.SakaiProjectWrapper;
import grader.assignment.GradingFeature;
import grader.checkers.*;
import grader.checkers.CheckResult;
import grader.sakai.project.SakaiProject;

/**
 * This wraps a feature in a test case so the "grader" checkers can run in the "framework" system.
 */
public class TestCaseWrapper extends BasicTestCase {

    private GradingFeature feature;
    private FeatureChecker featureChecker;

    public TestCaseWrapper(GradingFeature feature) {
        super(feature.getFeature() + " test case");
        this.feature = feature;
        featureChecker = feature.getFeatureChecker();
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // Get a SakaiProject out of the Project
        SakaiProject sakaiProject;
        if (project instanceof ProjectWrapper)
            sakaiProject = ((ProjectWrapper) project).getProject();
        else {
            sakaiProject = new SakaiProjectWrapper(project);
            sakaiProject.maybeMakeClassDescriptions();
        }
        featureChecker.setProject(sakaiProject);

        CheckResult result = featureChecker.check();
        if (result == null)
            throw new NotGradableException();
        String notes = "";
        for (String line : result.getLog())
            notes += (notes.isEmpty() ? "\n" : "") + line;
        return partialPass(result.getScore() / feature.getMax(), notes);
    }
}

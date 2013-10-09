package grader.checkers;

import util.misc.Common;

public class AProjectTracer extends AnAbstractFeatureChecker implements FeatureChecker {

    CheckResult checkResult = new ACheckResult();

    public AProjectTracer() {
        setOverridable(false);
    }


    public CheckResult check() {
        System.out.println("project folder:" + project.getProjectFolderName());
        System.out.println("output folder:" + project.getOutputFolder());
        System.out.println("source flder name:" + project.getSourceProjectFolderName());
        System.out.println("source file:" + project.getSourceFileName());
        System.out.println("input files:" + feature.getInputFiles());
        System.out.println("output files:" + feature.getOutputFiles());
        System.out.println("class descriptions:" + project.getClassesManager().getClassDescriptions());
        System.out.println("class text manager:" + project.getClassesTextManager().getAllSourcesText().substring(0, 20));
        System.out.println("name:" + project.getStudentAssignment().getStudentName());
        System.out.println("comments file:" + project.getStudentAssignment().getCommentsFile());
        System.out.println("time stamp:" + project.getStudentAssignment().getTimeStamp());
        System.out.println("date:" + project.getStudentAssignment().getDate());
        System.out.println("documents:" + project.getStudentAssignment().getDocuments());
        System.out.println("date:" + project.getStudentAssignment().getDate());
        System.out.println("feedback folder:" + project.getStudentAssignment().getFeedbackFolder());
        System.out.println("root cocde folder:" + project.getRootCodeFolder());
        System.out.println("sources folder name:" + project.getRootCodeFolder().getSourceProjectFolderName());
        System.out.println("binary folder name:" + project.getRootCodeFolder().getBinaryProjectFolderName());
        for (int i = 0; i < feature.getInputFiles().length; i++) {
            System.out.println("Input Text:" + Common.toText(feature.getInputFiles()[i]));
            System.out.println("Output Text:" + Common.toText(feature.getOutputFiles()[i]));

        }
        CheckResult result = new ACheckResult();
        result.setScore(feature.getMax());

        return result;
    }


}

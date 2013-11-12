package framework.wrappers;

import framework.logging.ConglomerateRecorder;
import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FeatureGradeRecorderFactory;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConglomerateRecorderFactory implements FeatureGradeRecorderFactory {
    @Override
    public FeatureGradeRecorder createGradeRecorder(SakaiProjectDatabase aSakaiProjectDatabase) {
        return new ConglomerateRecorder(aSakaiProjectDatabase);
    }
}

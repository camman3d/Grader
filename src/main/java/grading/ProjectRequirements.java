package grading;

import grading.testing.CheckResult;
import grading.testing.Checkable;
import grading.testing.Feature;
import project.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectRequirements {
    
    private List<Feature> features;
    private List<Feature> restrictions;

    public ProjectRequirements(List<Feature> features, List<Feature> restrictions) {
        this.features = features;
        this.restrictions = restrictions;
    }
}

package grader.checkers;

import bus.uigen.reflect.ClassProxy;
import bus.uigen.reflect.FieldProxy;
import grader.project.ClassDescription;

import java.lang.reflect.Modifier;
import java.util.List;

public class AnEncapsulationChecker extends AnAbstractFeatureChecker implements FeatureChecker {

    CheckResult checkResult;

    public CheckResult check() {
        checkPublicVariables();

        return checkResult;
    }

    void checkPublicVariables() {
        checkResult = new ACheckResult();
        List<ClassDescription> classDescriptions = project.getClassesManager().getClassDescriptions();
        int numVariables = 0;
        int numPublicVariables = 0;
        for (ClassDescription classDescription : classDescriptions) {
            ClassProxy classProxy = classDescription.getClassProxy();
            FieldProxy[] fields = classProxy.getDeclaredFields();
            numVariables += fields.length;
            for (FieldProxy field : fields) {
                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) && !Modifier.isFinal(modifiers)) {
                    checkResult.getLog().add("Public variable:" + classProxy.getSimpleName() + "." + field.getName());
                    numPublicVariables++;
                }
            }
        }
        double fractionPublicVariables = numVariables == 0 ? 0 : (double) numPublicVariables / numVariables;
        double fractionalScore = 1 - fractionPublicVariables;
        double score = feature.getMax() * fractionalScore;
        checkResult.setScore(score);
    }

}

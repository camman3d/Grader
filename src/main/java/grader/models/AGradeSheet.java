package grader.models;

import bus.uigen.ObjectEditor;
import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import util.annotations.ComponentWidth;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.misc.ASuffixFileFilter;
import util.models.AListenableVector;
import util.models.ListenableVector;

import javax.swing.*;
import java.io.File;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"StudentName", "StudentId", "Features", "Comment"})
// grade sheet takes as input data the names and max scores associated with various features
public class AGradeSheet {
    String studentName = "";
    String studentId = "";
    ListenableVector<GradingFeature> features;
    String comment = "None";
    JFileChooser fc = new JFileChooser();
    Object[][] featureDescriptions = {{"Tokens", 20}, {"Scanner", 50}};

    public static ListenableVector<GradingFeature> toFeatures(Object[][] featureDescriptions) {
        ListenableVector<GradingFeature> retVal = new AListenableVector();
        for (Object[] featureDescription : featureDescriptions) {
            retVal.add(new AGradingFeature((String) featureDescription[0], 10));

        }
        return retVal;
    }

    public AGradeSheet() {
        File file = new File("D:/dewan_backup/Java");
        fc.setCurrentDirectory(file);
        fc.setFileFilter(new ASuffixFileFilter(".zip"));
        features = toFeatures(featureDescriptions);
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String aStudentName) {
        this.studentName = aStudentName;
    }

    public ListenableVector<GradingFeature> getFeatures() {
        return features;
    }

    public void setFeatures(ListenableVector<GradingFeature> somefeatures) {
        this.features = somefeatures;

    }

    @ComponentWidth(300)
    public String getComment() {
        return comment;
    }

    public void setComment(String aComment) {
        this.comment = aComment;
    }

}

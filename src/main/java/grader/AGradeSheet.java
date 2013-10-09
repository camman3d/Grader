package grader;

import java.io.File;

import javax.swing.JFileChooser;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;
import util.misc.ASuffixFileFilter;
import util.models.AListenableVector;
import util.models.ListenableVector;

public class AGradeSheet {	
	String studentName = "";
	ListenableVector<Feature> features;
	String comment = "None";
//	int maxScore;
//	int score;
	JFileChooser fc = new JFileChooser();
//	int averageFeatureScore;
//	int averageTotalScore;
//	int totalScore;
//	int studentNumber;
	Object[][] featureDescriptions = {{"Tokens", 20}, {"Scanner", 50}};
	
	public static ListenableVector<Feature> toFeatures(Object[][] featureDescriptions) {
		ListenableVector<Feature> retVal = new AListenableVector();
		for (Object[] featureDescription: featureDescriptions) {
			retVal.add(new AFeature((String) featureDescription[0], (Integer) featureDescription[1])) ;
//			
//			if (featureDescription.length < 2) {
//				System.out.println("Feature description length = " + featureDescription.length);
//				continue;
//			}
			
		}
		return retVal;
	}
	
	public AGradeSheet() {
		File file = new File("D:/dewan_backup/Java");
		fc.setCurrentDirectory(file);
		fc.setFileFilter(new ASuffixFileFilter(".zip"));
		features = toFeatures(featureDescriptions);
	}
	@Row(0)
	@Column(0)
	public void next() {
		
	}
	@Row(0)
	@Column(1)
    public void repeat() {
		
	}
	@Row(0)
	@Column(2)
    public void open() {
		int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
        }
		
	}
	@Row(0)
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
//		studentNumber++;
//		feature = "";		
	}
//	@ComponentWidth(200)
	@Row(1)
	public ListenableVector<Feature> getFeatures() {
		return features;
	}	
	public void setFeatures(ListenableVector<Feature> features) {
		this.features = features;
		
	}
	
//	@Row(2)
//	public int getMaxScore() {
//		return maxScore;
//	}
//	public void setMaxScore(int maxScore) {
//		this.maxScore = maxScore;
//	}
//	@Row(3)
//	public int getScore() {
//		return score;
//	}
//	public void setScore(int score) {
//		this.score = score;
//	
//	}
	@Row(2)
	@ComponentWidth(300)
	public String getComment() {
		return comment;
	}	
	public void setComment(String comment) {
		this.comment = comment;
	}
//	public int getAverageFeatureScore() {
//		return averageFeatureScore;
//	}
//	public void setAverageFeatureScore(int averageFeatureScore) {
//		this.averageFeatureScore = averageFeatureScore;
//	}
//	public int getAverageTotalScore() {
//		return averageTotalScore;
//	}
//	public void setAverageTotalScore(int averageTotalScore) {
//		this.averageTotalScore = averageTotalScore;
//	}
//	public int getTotalScore() {
//		return totalScore;
//	}
//	public void setTotalScore(int totalScore) {
//		this.totalScore = totalScore;
//	}
//	public int getStudentNumber() {
//		return studentNumber;
//	}
//	public void setStudentNumber(int studentNumber) {
//		this.studentNumber = studentNumber;
//	}
	

	
	
	

}

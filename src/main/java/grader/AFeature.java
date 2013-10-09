package grader;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import bus.uigen.ObjectEditor;
import bus.uigen.attributes.AttributeNames;

public class AFeature implements Feature {
	String feature = "Some feature";
	String comment = " ";
	int maxScore;
	int score;
	boolean graded;
	public AFeature(String aFeature, int aMaxScore) {
		feature = aFeature;
		score = aMaxScore;
	}
//	@Column(0)
	@ComponentWidth(100)
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
//	@Column(1)
	@ComponentWidth(30)
	public int getMax() {
		return maxScore;
	}
	public void setMax(int maxScore) {
		this.maxScore = maxScore;
	}
//	@Column(2)
	@ComponentWidth(40)
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
		graded = true;
	}
//	public boolean isGraded() {
//		return graded;
//	}
//	@Column(3)
	public void correct() {
		setScore(maxScore);
	}
	public String getComment() {
		return comment;
	}
//	@Column(4)
	public void setComment(String aComment) {
		comment = aComment;
	}
	
	public static void main (String[] args) {
		ObjectEditor.setMethodAttribute(AFeature.class, "correct", AttributeNames.SHOW_BUTTON, true);
		ObjectEditor.edit(new AFeature("foo", 50));
	}
	

}

package grader.checkers;

public class AFailingChecker extends AnAbstractFeatureChecker implements FeatureChecker{

	@Override
	public CheckResult check() {
//		if (Math.random() > 0.5)
			return null;
//		CheckResult checkResult = new ACheckResult();
//		checkResult.setScore(feature.getMax());
//		return checkResult;
	}

}

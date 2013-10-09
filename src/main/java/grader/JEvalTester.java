package grader;

import net.sourceforge.jeval.Evaluator;

public class JEvalTester {
	public static void main (String[] args) {
		Evaluator evaluator = new Evaluator();
		try {
		System.out.println(evaluator
				.evaluate("toLowerCase('Hello World!')"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

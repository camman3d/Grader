package grader.checkers;

import java.util.List;

public interface CheckResult {
    public double getScore();

    public void setLog(List<String> log);

    public void setScore(double score);

    public List<String> getLog();

}

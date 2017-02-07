package ca.ingeno.hackothon.tester;

import java.util.ArrayList;
import java.util.List;

public class TeamResult {

  private int totalTestCount;
  private int totalTestPassed;
  private String teamName;
  private List<String> passedTest;
  private List<String> failedTest;

  public TeamResult(String teamName) {
    this.teamName = teamName;
    totalTestCount = 0;
    totalTestPassed = 0;
    passedTest = new ArrayList<>();
    failedTest = new ArrayList<>();
  }

  public void addTest(TestResult test) {
    totalTestCount += 1;
    if (test.isPassed()) {
      totalTestPassed += 1;
      passedTest.add(test.getTestName());
    } else {
      failedTest.add(test.getTestName());
    }
  }

  public List<String> getPassedTest() {
    return passedTest;
  }

  public List<String> getFailedTest() {
    return failedTest;
  }

  public String getTeamName() {
    return teamName;
  }

  public String getScore() {
    return totalTestPassed + "/" + totalTestCount;
  }
}

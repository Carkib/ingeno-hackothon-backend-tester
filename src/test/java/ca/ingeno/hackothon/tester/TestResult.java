package ca.ingeno.hackothon.tester;

public class TestResult {

  private String testName;
  private boolean passed;
  private boolean hidden;

  public TestResult(String testName, boolean passed) {
    this.testName = testName;
    this.passed = passed;
    this.hidden = false;
  }

  public String getTestName() {
    return testName;
  }

  public boolean isPassed() {
    return passed;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }
}

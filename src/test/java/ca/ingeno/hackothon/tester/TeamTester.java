package ca.ingeno.hackothon.tester;

import ca.ingeno.hackothon.tester.request.RequestSender;
import ca.ingeno.hackothon.tester.tests.ParcometreTests;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class TeamTester {

  private TeamResult teamResult;
  private List<Function<String, TestResult>> tests;

  public TeamTester(String teamName) {
    teamResult = new TeamResult(teamName);
    tests = new ArrayList<>();
    tests.add(this::APassedTest);
    tests.add(this::AFailedTest);
    tests.add(this::ARandomTest);
    tests.add(this::specificLocationNearestParcometreTest);
  }

  public TeamResult test(String endPoint) {
    for (Function<String, TestResult> testFunction : tests) {
      teamResult.addTest(testFunction.apply(endPoint));
    }
    return teamResult;
  }

  public TestResult APassedTest(String endpoint) {
    //Yeah! It passed :D
    return new TestResult("APassedTest", true);
  }

  public TestResult AFailedTest(String endpoint) {
    //Bouu.. It failed :(
    return new TestResult("AFailedTest", false);
  }

  public TestResult ARandomTest(String endpoint) {
    //Just to see the reactivity
    Random random = new Random();
    boolean result = random.nextBoolean();
    return new TestResult("ARandomTest", result);
  }

  public TestResult specificLocationNearestParcometreTest(String endpoint) {
      return ParcometreTests.specificLocationNearestParcometreTest(endpoint);
  }
}

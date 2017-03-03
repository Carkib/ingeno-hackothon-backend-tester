package ca.ingeno.hackothon.tester;

import ca.ingeno.hackothon.tester.tests.ParcometreTests;
import ca.ingeno.hackothon.tester.tests.ParkingZoneTests;

import java.util.ArrayList;
import java.util.Arrays;
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
    tests.add(this::AHiddenTest);
    tests.add(ParcometreTests::givenASpecificLocationReturnTheNearestParcometre);
    tests.add(ParcometreTests::canSpecifyASpecificQuantityOfPointsToReturn);
    tests.add(ParcometreTests::canSpecifyARadiusConstraint);
    tests.add(ParcometreTests::edgeCases);

    tests.addAll(Arrays.asList(
        ParkingZoneTests::givenASpecificLocationIsInsideParkingZone01,
        ParkingZoneTests::givenASpecificLocationIsOutsideParkingZone01,
        ParkingZoneTests::givenASpecificLocationIsInsideParkingZone12,
        ParkingZoneTests::givenASpecificLocationIsOutsideParkingZone12,
        ParkingZoneTests::givenASpecificLocationIsInsideParkingZone15,
        ParkingZoneTests::givenASpecificLocationIsOutsideParkingZone15));
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

  public TestResult AHiddenTest(String endpoint) {
    //shhh, it's hidden
    TestResult result = new TestResult("This won't be seen...", true);
    result.setHidden(true);
    return result;
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
}

package ca.ingeno.hackothon.tester.tests;

import ca.ingeno.hackothon.tester.TestResult;
import ca.ingeno.hackothon.tester.request.RequestSender;
public final class ParcometreTests
{
    private ParcometreTests() {
    }

    public static TestResult specificLocationNearestParcometreTest(String endpoint) {
        String jsonResponse = RequestSender.getRequestAtUrl("http://" + endpoint + "/nearest/?location=-71.2266796885977,46.8125494145");
        boolean result = false;
        if (jsonResponse.contains("-71.2266796885977") && jsonResponse.contains("46.8125494143152"))
        {
            result = true;
        }

        return new TestResult("nearest point test", result);
    }
}

package ca.ingeno.hackothon.tester.tests;

import ca.ingeno.hackothon.tester.TestResult;
import ca.ingeno.hackothon.tester.request.HTTPRequestSender;
public final class ParcometreTests
{

    private static final String SPECIFIC_LOCATION = "/nearest/?location=-71.2266796885977,46.8125494145";
    private static final String NEAREST_LONG = "-71.2266796885977";
    private static final String NEAREST_LAT = "46.8125494143152";


    private ParcometreTests() {
    }

    public static TestResult givenAspecificLocationReturnTheNearestParcometre(String endpoint) {
        String jsonResponse = HTTPRequestSender.getRequestAtUrl("http://" + endpoint + SPECIFIC_LOCATION);
        boolean result = false;
        if (jsonResponse.contains(NEAREST_LONG) && jsonResponse.contains(NEAREST_LAT))
        {
            result = true;
        }

        return new TestResult("Given a specific location return the nearest parking meter", result);
    }
}

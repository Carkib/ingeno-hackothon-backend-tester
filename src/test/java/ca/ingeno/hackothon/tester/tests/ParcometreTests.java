package ca.ingeno.hackothon.tester.tests;

import ca.ingeno.hackothon.tester.TestResult;
import ca.ingeno.hackothon.tester.request.FailedTestException;
import ca.ingeno.hackothon.tester.request.HTTPRequestSender;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;

public final class ParcometreTests
{

    private static final String INGENO_LOCATION = "/nearest/?location=-71.225571,46.813562&quantity=2";

    private static final String CLOSE_TO_INGENO_LONG1 = "-71.2255202738255";

    private static final String CLOSE_TO_INGENO_LAT1 = "46.8134798367104";

    private static final String CLOSE_TO_INGENO_LONG2 = "-71.225501686668";

    private static final String CLOSE_TO_INGENO_LAT2 = "46.8134872146295";

    private static final String SPECIFIC_LOCATION = "/nearest/?location=-71.2266796885977,46.8125494145";

    private static final String NEAREST_LONG = "-71.2266796885977";

    private static final String NEAREST_LAT = "46.8125494143152";

    private ParcometreTests()
    {
    }

    public static TestResult givenASpecificLocationReturnTheNearestParcometre(String endpoint)
    {
        boolean result = false;
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + SPECIFIC_LOCATION);
            if (list_of_points.size() == 1)
            {
                result = geojsonHelper.checkIfListOfFeaturesContainsPoint(list_of_points, NEAREST_LONG, NEAREST_LAT);
            }
        }
        catch (Exception e)
        {
            result = false;
        }

        return new TestResult("Given a specific location return the nearest parking meter", result);
    }

    public static TestResult askingForTheTwoClosestPointFromIngenoReturnsTheTwoCorrectParkings(String endpoint)
    {
        boolean result = false;
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + INGENO_LOCATION);
            if (list_of_points.size() == 2)
            {
                result = geojsonHelper.checkIfListOfFeaturesContainsPoint(list_of_points, CLOSE_TO_INGENO_LONG1, CLOSE_TO_INGENO_LAT1);
                result = result && geojsonHelper.checkIfListOfFeaturesContainsPoint(list_of_points, CLOSE_TO_INGENO_LONG2, CLOSE_TO_INGENO_LAT2);
            }
        }
        catch (Exception e)
        {
            result = false;
        }

        return new TestResult("Given a specific location return the nearest parking meter", result);
    }

    private static List<JsonNode> getFeaturesAtURL(String url) throws FailedTestException, IOException
    {
        String jsonResponse = HTTPRequestSender.getRequestAtUrl(url);
        return geojsonHelper.getListOfPoints(jsonResponse);
    }
}

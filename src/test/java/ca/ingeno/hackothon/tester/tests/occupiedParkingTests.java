package ca.ingeno.hackothon.tester.tests;

import ca.ingeno.hackothon.tester.TestResult;
import ca.ingeno.hackothon.tester.request.FailedTestException;
import ca.ingeno.hackothon.tester.request.HTTPRequestSender;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public class occupiedParkingTests
{
    private static final String NEAREST_LONG_BEFORE_10 = "-71.2209548908844";

    private static final String NEAREST_LAT_BEFORE_10 = "46.8130771279971";

    private static final String NEAREST_LONG_BEFORE_11 = "-71.2209321772754";

    private static final String NEAREST_LAT_BEFORE_11 = "46.8130846391239";

    private static final String BORIS_LOCATION_CLOSEST = "/nearest/?location=-71.221213,46.812228";

    private occupiedParkingTests()
    {
    }

    public static TestResult nearestAtAnyTimeReturnTheAppropriateParking(String endpoint)
    {
        boolean result = false;
        if (getHoursOfTheDay() < 10)
        {
            result = returnTheClosestPoint(endpoint, NEAREST_LONG_BEFORE_10, NEAREST_LAT_BEFORE_10);
        }

        if (getHoursOfTheDay() >= 10 && getHoursOfTheDay() < 11)
        {
            result = returnTheClosestPoint(endpoint, NEAREST_LONG_BEFORE_11,  NEAREST_LAT_BEFORE_11);
        }
        return new TestResult("The nearest route, at any time, return the appropriate parking", result);
    }

    private static boolean returnTheClosestPoint(String endpoint, String longi, String lat)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + BORIS_LOCATION_CLOSEST);
            return listContainTheClosestPoint(list_of_points,longi, lat);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean returnTheClosestPointBefore11(String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + BORIS_LOCATION_CLOSEST);
            return listContainTheClosestPoint(list_of_points);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static List<JsonNode> getFeaturesAtURL(String url) throws FailedTestException, IOException
    {
        String jsonResponse = HTTPRequestSender.getRequestAtUrl(url);
        return geojsonHelper.getListOfPoints(jsonResponse);
    }

    private static boolean listContainTheClosestPoint(List<JsonNode> list_of_points, String longi, String lat)
    {
        boolean firstPointPresent = geojsonHelper
                .checkIfListOfFeaturesContainsPoint(list_of_points, longi, lat);
        return list_of_points.size() == 1 && firstPointPresent;
    }

    private static int getHoursOfTheDay()
    {
        DateTime dt = new DateTime();
        return dt.getHourOfDay();
    }
}

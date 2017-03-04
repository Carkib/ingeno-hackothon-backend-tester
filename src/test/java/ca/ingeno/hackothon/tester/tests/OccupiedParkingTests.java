package ca.ingeno.hackothon.tester.tests;

import ca.ingeno.hackothon.tester.TestResult;
import ca.ingeno.hackothon.tester.request.FailedTestException;
import ca.ingeno.hackothon.tester.request.HTTPRequestSender;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;

public class OccupiedParkingTests
{
    private static final String NEAREST_LONG_BEFORE_10 = "-71.2209548908844";

    private static final String NEAREST_LAT_BEFORE_10 = "46.8130771279971";

    private static final String NEAREST_LONG_BEFORE_11 = "-71.2209321772754";

    private static final String NEAREST_LAT_BEFORE_11 = "46.8130846391239";

    private static final String NEAREST_LONG_BEFORE_13 = "-71.2208147255085";

    private static final String NEAREST_LAT_BEFORE_13 = "46.8131286965589";

    private static final String BORIS_LOCATION_CLOSEST = "/nearest?location=-71.221213,46.812228";

    private static final String PARKING_ESTIMAUVILLE_LONG = "-71.2112434853845";

    private static final String PARKING_ESTIMAUVILLE_LAT = "46.8427720148002";

    private static final String A_PARKING_NEAR_ESTIMAUVILLE = "/nearest?location=-71.2112562302213,46.8427830011235";

    private static final String A_PARKING_SAINT_PAUL_LONG = "-71.214475345103";

    private static final String A_PARKING_SAINT_PAUL_LAT = "46.8168303308662";

    private static final String SECOND_PARKING_SAINT_PAUL_LONG = "-71.2144677710821";

    private static final String SECOND_PARKING_SAINT_PAUL_LAT = "46.8168194925062";

    private static final String A_PARKING_NEAR_SAINT_PAUL = "/nearest?location=-71.214475345103,46.8168303308662";

    private static final String SECRET_LOCATION = "/nearest/secret?location=-71.2126272666451,46.8176939819953";

    private static final String SECRET_LOCATION_LONG = "-71.2126589543274";

    private static final String SECRET_LOCATION_LAT = "46.817588814758";


    private OccupiedParkingTests()
    {
    }

    public static TestResult theNearestRouteConsiderMessageSentByTheCommunity(String endpoint)
    {
        boolean result =
                nearestAtAnyTimeReturnTheAppropriateParkingForBoris(endpoint) && nearestAtAnytimeReturnTheAppropriateParkingSaintPaul(
                        endpoint);
        return new TestResult("The nearest route uses messages sent by the community", result);
    }

    public static TestResult secretRoute(String endpoint)
    {
        boolean result =returnTheClosestSecretPoint(endpoint);

        TestResult secretResult = new TestResult("Secret test", result);
        secretResult.setHidden(true);
        return secretResult;
    }

    private static boolean nearestAtAnyTimeReturnTheAppropriateParkingForBoris(String endpoint)
    {
        boolean result = false;
        if (getHoursOfTheDay() < 10)
        {
            result = returnTheClosestPointBoris(endpoint, NEAREST_LONG_BEFORE_10, NEAREST_LAT_BEFORE_10);
        }

        if (getHoursOfTheDay() >= 10 && getHoursOfTheDay() < 11)
        {
            result = returnTheClosestPointBoris(endpoint, NEAREST_LONG_BEFORE_11, NEAREST_LAT_BEFORE_11);
        }

        if (getHoursOfTheDay() >= 11)
        {
            result = returnTheClosestPointBoris(endpoint, NEAREST_LONG_BEFORE_13, NEAREST_LAT_BEFORE_13);
        }

        if (getHoursOfTheDay() >= 16)
        {
            result = result && returnTheFinalClosestPoint(endpoint);
        }

        return result;
    }

    private static boolean nearestAtAnytimeReturnTheAppropriateParkingSaintPaul(String endpoint)
    {
        boolean result = false;
        if (getHoursOfTheDay() >= 11 && getHoursOfTheDay() < 13)
        {
            result = returnTheClosestPointSaintPaul(endpoint, SECOND_PARKING_SAINT_PAUL_LONG,
                    SECOND_PARKING_SAINT_PAUL_LAT);
        }
        else
        {
            result = returnTheClosestPointSaintPaul(endpoint, A_PARKING_SAINT_PAUL_LONG, A_PARKING_SAINT_PAUL_LAT);
        }

        return result;
    }

    private static boolean returnTheClosestPointBoris(String endpoint, String longi, String lat)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + BORIS_LOCATION_CLOSEST);
            return listContainTheClosestPoint(list_of_points, longi, lat);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean returnTheClosestPointSaintPaul(String endpoint, String longi, String lat)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + A_PARKING_NEAR_SAINT_PAUL);
            return listContainTheClosestPoint(list_of_points, longi, lat);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean returnTheClosestSecretPoint(String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + SECRET_LOCATION);
            return listContainTheClosestPoint(list_of_points, SECRET_LOCATION_LONG, SECRET_LOCATION_LAT);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean returnTheFinalClosestPoint(String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + A_PARKING_NEAR_ESTIMAUVILLE);
            return listContainTheClosestPoint(list_of_points, PARKING_ESTIMAUVILLE_LONG, PARKING_ESTIMAUVILLE_LAT);
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
        boolean firstPointPresent = geojsonHelper.checkIfListOfFeaturesContainsPoint(list_of_points, longi, lat);
        return list_of_points.size() == 1 && firstPointPresent;
    }

    private static int getHoursOfTheDay()
    {
        DateTime dt = new DateTime();
        return dt.getHourOfDay();
    }
}

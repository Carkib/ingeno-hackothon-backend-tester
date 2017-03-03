package ca.ingeno.hackothon.tester.tests;

import ca.ingeno.hackothon.tester.TestResult;
import ca.ingeno.hackothon.tester.request.FailedTestException;
import ca.ingeno.hackothon.tester.request.HTTPRequestSender;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;

public final class ParcometreTests
{

    private static final String INGENO_LOCATION_2_CLOSEST = "/nearest?location=-71.225571,46.813562&quantity=2";

    private static final String INGENO_LOCATION_RADIUS_OF_15 = "/nearest?location=-71.225571,46.813562&radius=15";

    private static final String INGENO_LOCATION_RADIUS_OF_15_QUANTITY_OF_10 = "/nearest?location=-71.225571,46.813562&radius=15&quantity=10";

    private static final String INGENO_LOCATION_RADIUS_OF_1 = "/nearest?location=-71.225571,46.813562&radius=1";

    private static final String SPECIFIC_LOCATION = "/nearest?location=-71.2266796885977,46.8125494145";

    private static final String SPECIFIC_LOCATION_10_CLOSEST = "/nearest?location=-71.2266796885977,46.8125494145&quantity=10";

    private static final String SPECIFIC_LOCATION_RADIUS_100000_QUANTITY_1 = "/nearest?location=-71.2266796885977,46.8125494145&radius=100000&quantity=1";

    private static final String CLOSE_TO_INGENO_LONG1 = "-71.2255202738255";

    private static final String CLOSE_TO_INGENO_LAT1 = "46.8134798367104";

    private static final String CLOSE_TO_INGENO_LONG2 = "-71.225501686668";

    private static final String CLOSE_TO_INGENO_LAT2 = "46.8134872146295";

    private static final String NEAREST_LONG = "-71.2266796885977";

    private static final String NEAREST_LAT = "46.8125494143152";

    private ParcometreTests()
    {
    }

    public static TestResult givenASpecificLocationReturnTheNearestParcometre(String endpoint)
    {
        boolean result = askingForTheNearestPointReturnsIt(endpoint);
        return new TestResult("Given a specific location, return the nearest parking meter", result);
    }

    public static TestResult canSpecifyASpecificQuantityOfPointsToReturn(String endpoint)
    {
        boolean result =
                askingForTheTwoClosestPointFromIngenoReturnsTheTwoCorrectParkings(endpoint) && canAskFor10Points(
                        endpoint);
        return new TestResult("Adding a specific quantity, the correct numbers of points are returned", result);
    }

    public static TestResult canSpecifyARadiusConstraint(String endpoint)
    {
        boolean result = askingForTheClosestPointsFromIngenoWithARadiusOf1WillReturnAnEmptyList(endpoint)
                && askingForThePointsInARadiusOf15MetersFromIngenoReturnsTheTwoCorrectParkings(endpoint);
        return new TestResult("Adding a radius will limit the number of points returned", result);
    }

    public static TestResult edgeCases(String endpoint)
    {
        boolean result =
                askingForThePointsInARadiusOf15MetersAndQuantityOf10FromIngenoReturnsTheTwoCorrectParkings(endpoint)
                        && askingForTheNearestPointWithAHighRadiusButQuantity1ReturnsTheNearestPoint(endpoint);
        return new TestResult("Endpoint Edge cases are covered", result);
    }

    private static boolean askingForTheNearestPointReturnsIt(String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + SPECIFIC_LOCATION);
            return listContainTheClosestPoint(list_of_points);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean askingForTheTwoClosestPointFromIngenoReturnsTheTwoCorrectParkings(String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + INGENO_LOCATION_2_CLOSEST);
            return listContainTwoClosestPointsFromIngeno(list_of_points);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean canAskFor10Points(String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + SPECIFIC_LOCATION_10_CLOSEST);
            return list_of_points.size() == 10;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean askingForTheClosestPointsFromIngenoWithARadiusOf1WillReturnAnEmptyList(String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + INGENO_LOCATION_RADIUS_OF_1);
            return list_of_points.isEmpty();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean askingForThePointsInARadiusOf15MetersFromIngenoReturnsTheTwoCorrectParkings(String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL("http://" + endpoint + INGENO_LOCATION_RADIUS_OF_15);
            return listContainTwoClosestPointsFromIngeno(list_of_points);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean askingForThePointsInARadiusOf15MetersAndQuantityOf10FromIngenoReturnsTheTwoCorrectParkings(
            String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL(
                    "http://" + endpoint + INGENO_LOCATION_RADIUS_OF_15_QUANTITY_OF_10);
            return listContainTwoClosestPointsFromIngeno(list_of_points);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private static boolean askingForTheNearestPointWithAHighRadiusButQuantity1ReturnsTheNearestPoint(String endpoint)
    {
        try
        {
            List<JsonNode> list_of_points = getFeaturesAtURL(
                    "http://" + endpoint + SPECIFIC_LOCATION_RADIUS_100000_QUANTITY_1);
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

    private static boolean listContainTwoClosestPointsFromIngeno(List<JsonNode> list_of_points)
    {
        boolean firstPointPresent = geojsonHelper
                .checkIfListOfFeaturesContainsPoint(list_of_points, CLOSE_TO_INGENO_LONG1, CLOSE_TO_INGENO_LAT1);
        boolean secondPointPresent = geojsonHelper
                .checkIfListOfFeaturesContainsPoint(list_of_points, CLOSE_TO_INGENO_LONG2, CLOSE_TO_INGENO_LAT2);
        return list_of_points.size() == 2 && firstPointPresent && secondPointPresent;
    }

    private static boolean listContainTheClosestPoint(List<JsonNode> list_of_points)
    {
        boolean firstPointPresent = geojsonHelper
                .checkIfListOfFeaturesContainsPoint(list_of_points, NEAREST_LONG, NEAREST_LAT);
        return list_of_points.size() == 1 && firstPointPresent;
    }
}

package ca.ingeno.hackothon.tester.tests;

import ca.ingeno.hackothon.tester.TestResult;
import ca.ingeno.hackothon.tester.request.FailedTestException;
import ca.ingeno.hackothon.tester.request.HTTPRequestSender;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;

public class ParkingZoneTests {

    private static final String ZONE_ENDPOINT = "/parkinzone?sign_type=";

    private static final String ZONE_01_CODES = "PS1291,PS1292,PS1292,PS8002";
    private static final String ZONE_12_CODES = "PS7203,PS2151,PS2152,PS2153";
    private static final String ZONE_15_CODES = "PS2762,PS2763,PS2761";

    private static final String TEST_01_LAT = "46.811139";
    private static final String TEST_01_LONG = "-71.222405";
    private static final String TEST_01_LAT_FAIL = "46.812136";
    private static final String TEST_01_LONG_FAIL = "-71.222945";

    private static final String TEST_12_LAT = "46.815177";
    private static final String TEST_12_LONG = "-71.224293";
    private static final String TEST_12_LAT_FAIL = "46.821638";
    private static final String TEST_12_LONG_FAIL = "-71.227205";

    private static final String TEST_15_LAT = "46.805135";
    private static final String TEST_15_LONG = "-71.205687";
    private static final String TEST_15_LAT_FAIL = "46.804979";
    private static final String TEST_15_LONG_FAIL = "-71.204555";

    public static TestResult givenASpecificLocationIsInsideParkingZone01(String endpoint) {
        boolean result = isLocationInsideTheZone(endpoint, ZONE_01_CODES, TEST_01_LAT, TEST_01_LONG);
        return new TestResult("Given signalisation type code return the nearest parking meter", result);
    }

    public static TestResult givenASpecificLocationIsOutsideParkingZone01(String endpoint) {
        boolean result = isLocationInsideTheZone(endpoint, ZONE_01_CODES, TEST_01_LAT_FAIL, TEST_01_LONG_FAIL);
        return new TestResult("Given a specific location return the nearest parking meter", !result);
    }

    public static TestResult givenASpecificLocationIsInsideParkingZone12(String endpoint) {
        boolean result = isLocationInsideTheZone(endpoint, ZONE_12_CODES, TEST_12_LAT, TEST_12_LONG);
        return new TestResult("Given a specific location return the nearest parking meter", result);
    }

    public static TestResult givenASpecificLocationIsOutsideParkingZone12(String endpoint) {
        boolean result = isLocationInsideTheZone(endpoint, ZONE_12_CODES, TEST_12_LAT_FAIL, TEST_12_LONG_FAIL);
        return new TestResult("Given a specific location return the nearest parking meter", !result);
    }

    public static TestResult givenASpecificLocationIsInsideParkingZone15(String endpoint) {
        boolean result = isLocationInsideTheZone(endpoint, ZONE_15_CODES, TEST_15_LAT, TEST_15_LONG);
        return new TestResult("Given a specific location return the nearest parking meter", result);
    }

    public static TestResult givenASpecificLocationIsOutsideParkingZone15(String endpoint) {
        boolean result = isLocationInsideTheZone(endpoint, ZONE_15_CODES, TEST_15_LAT_FAIL, TEST_15_LONG_FAIL);
        return new TestResult("Given a specific location return the nearest parking meter", !result);
    }

    private static boolean isLocationInsideTheZone(String endpoint, String zoneCodes, String locationLat, String locationLong) {
        boolean result = false;
        try {
            List<JsonNode> nodes =  getFeaturesAtURL("http://" + endpoint + ZONE_ENDPOINT + zoneCodes);
            if (nodes.size() == 1) {
                result = geojsonHelper.checkIfPointIsInTheZone(nodes.get(0), locationLat, locationLong);
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private static List<JsonNode> getFeaturesAtURL(String url) throws FailedTestException, IOException {
        String jsonResponse = HTTPRequestSender.getRequestAtUrl(url);
        return geojsonHelper.getListOfPoints(jsonResponse);
    }

}

package ca.ingeno.hackothon.tester.tests;

import ca.ingeno.hackothon.tester.request.HTTPRequestSender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class geojsonHelper
{
    public static List<JsonNode> getListOfPoints(String jsonResponse) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonResponse);
        List<JsonNode> list_of_points = actualObj.findValue("features").findValues("geometry");

        return list_of_points;
    }

    public static boolean checkIfListOfFeaturesContainsPoint(List<JsonNode> points, String longitude, String latitude)
    {
        for (JsonNode point: points)
        {
            String pointStr = point.findValue("coordinates").toString();
            if(pointStr.contains(longitude) && pointStr.contains(latitude))
            {
                return true;
            }
        }
        return false;
    }
}

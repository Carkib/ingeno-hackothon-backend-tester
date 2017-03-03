package ca.ingeno.hackothon.tester.tests;

import ca.ingeno.hackothon.tester.TestStatus;
import ca.ingeno.hackothon.tester.utils.polygon.Point;
import ca.ingeno.hackothon.tester.utils.polygon.Polygon;
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

    public static TestStatus checkIfPointIsInTheZone(JsonNode polygonCoordinates, String latitude, String longitude) {
        Polygon polygon = buildPolygonFromCoordinates(polygonCoordinates);
        Point point = new Point(getFloatValue(longitude), getFloatValue(latitude));


        return (polygon.contains(point)) ? TestStatus.SUCCESS : TestStatus.FAILURE;
    }

    private static Polygon buildPolygonFromCoordinates(JsonNode coordinates) {
        Polygon.Builder builder = Polygon.Builder();

        List<JsonNode> nodes = coordinates.findValues("coordinates");
        nodes.get(0).get(0).forEach(
                coordinate->builder.addVertex(
                        new Point(getFloatValue(coordinate.get(0).asText()), getFloatValue(coordinate.get(1).asText()))
                )
        );
        return builder.build();
    }

    private static float getFloatValue(String node) {
        return Float.valueOf(node);
    }
}

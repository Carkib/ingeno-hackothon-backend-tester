package ca.ingeno.hackothon.tester;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResultWriter {

  public static void WriteResults(List<TeamResult> teamResults) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(new File("results.json"), teamResults);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}

package ca.ingeno.hackothon.tester;

public class RemoteTest {

  private static final String testUrl = System.getProperty("testUrl", "http://localhost");
  private static final String testPort = System.getProperty("testPort", "8080");

  private static String baseUrl = testUrl + ":" + testPort;

  public String getBaseUrl() {
    return baseUrl;
  }
}

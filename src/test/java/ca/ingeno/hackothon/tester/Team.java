package ca.ingeno.hackothon.tester;

public class Team {

  private String endPoint;
  private String teamName;

  public Team(String endPoint, String teamName) {
    this.endPoint = endPoint;
    this.teamName = teamName;
  }

  public String getName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public String getEndPoint() {

    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }
}

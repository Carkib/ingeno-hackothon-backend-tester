package ca.ingeno.hackothon.tester;

import ca.ingeno.hackothon.tester.io.FileReaderFactory;
import ca.ingeno.hackothon.tester.io.FileRepository;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class MainTester {

  public static void main(String[] args) {
    MainTester tester = new MainTester();
    System.out.println("Started.");
    do {
      tester.test();
      System.out.println("Updated results at " + new SimpleDateFormat("hh:mm:ss").format(new Date()));
    } while (true);
  }

  @Test
  public void test() {
    List<Team> teams = GetTeams();
    List<TeamResult> teamResults = new ArrayList<>();
    for (Team team : teams) {
      TeamTester teamTester = new TeamTester(team.getName());
      TeamResult teamResult = teamTester.test(team.getEndPoint());
      teamResults.add(teamResult);
    }
    ResultWriter.WriteResults(teamResults);
  }

  private List<Team> GetTeams() {
    FileRepository<Team> repo = new FileRepository<>(FileReaderFactory.getFileReader());
    repo.setFileName("teams.txt");
    return repo.findAll(new Function<String, Team>() {
      @Override
      public Team apply(String stringifiedTeam) {
        String[] splitedStringifiedTeam = stringifiedTeam.split(",");
        return new Team(
            splitedStringifiedTeam[0],
            splitedStringifiedTeam[1]);
      }
    });
  }

}

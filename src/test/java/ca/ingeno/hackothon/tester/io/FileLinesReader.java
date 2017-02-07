package ca.ingeno.hackothon.tester.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileLinesReader {

  public Stream<String> readLines(String fileName) throws IOException {
    return Files.lines(Paths.get(fileName));
  }

}

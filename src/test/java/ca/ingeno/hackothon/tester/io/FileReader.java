package ca.ingeno.hackothon.tester.io;

import java.io.IOException;
import java.util.stream.Stream;

public class FileReader {

  private FileLinesReader fileLinesReader;

  public FileReader(FileLinesReader fileLinesReader) {
    this.fileLinesReader = fileLinesReader;
  }

  public Stream<String> read(String fileName) {
    try {
      return fileLinesReader.readLines(fileName);
    } catch (IOException ex) {
      throw new UnreadableFileException(
          "ERROR : Cannot read " + fileName + ". You'll get an empty stream, sorry!");
    }
  }
}

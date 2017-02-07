package ca.ingeno.hackothon.tester.io;

public class FileReaderFactory {

  public static FileReader getFileReader() {
    return new FileReader(new FileLinesReader());
  }
}

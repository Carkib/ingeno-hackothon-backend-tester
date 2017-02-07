package ca.ingeno.hackothon.tester.io;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileRepository<T> {

  private FileReader fileReader;

  private String fileName;

  public FileRepository(FileReader fileReader) {
    this.fileReader = fileReader;
  }

  public List<T> findAll(Function<String, T> transformer) {
    Stream<String> linesStream = fileReader.read(fileName);
    return linesStream.map(transformer).collect(Collectors.toList());
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}

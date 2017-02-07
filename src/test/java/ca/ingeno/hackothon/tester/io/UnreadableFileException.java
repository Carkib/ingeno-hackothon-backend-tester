package ca.ingeno.hackothon.tester.io;

public class UnreadableFileException extends RuntimeException {

  String message;

  public UnreadableFileException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}


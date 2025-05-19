package events;

import java.util.EventObject;

public abstract class ResponseEvent extends EventObject {
  public enum Status {
    SUCCESS("SUCCESS"),
    FAILURE("FAILED"),
    NOT_FOUND("NOT_FOUND"),
    INVALID_INPUT("INVALID_INPUT");

    private final String message;

    Status(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }

  private String response;

  public ResponseEvent(Object source) {
    super(source);
  }

  public String getResponse() {
    return this.response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public void setResponse(Status status) {
    this.response = status.getMessage();
  }
}

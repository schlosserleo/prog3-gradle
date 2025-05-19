package events;

import java.util.EventObject;

public abstract class ResponseEvent extends EventObject {

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
}

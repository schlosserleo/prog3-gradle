package events;

public class DeleteCakeEvent extends ResponseEvent {

  private final int location;

  public DeleteCakeEvent(Object source, int location) {
    super(source);
    this.location = location;
  }

  public int getLocation() {
    return this.location;
  }
}

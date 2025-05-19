package events;

public class InspectCakeEvent extends ResponseEvent{
  private final int location;

  public InspectCakeEvent(Object source, int location) {
    super(source);
    this.location = location;
  }

  public int getLocation() {
    return location;
  }
}

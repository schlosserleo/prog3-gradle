package events;

public class CreateHerstellerEvent extends ResponseEvent {

  private final String herstellerName;

  public CreateHerstellerEvent(Object source, String herstellerName) {
    super(source);
    this.herstellerName = herstellerName;
  }

  public String getHerstellerName() {
    return this.herstellerName;
  }
}

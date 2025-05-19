package listeners;

import domainLogic.HerstellerImpl;
import domainLogic.HerstellerVerwaltung;
import events.CreateHerstellerEvent;

public class CreateHerstellerEventListener implements GenericListener<CreateHerstellerEvent> {

  private final HerstellerVerwaltung herstellerVerwaltung;

  public CreateHerstellerEventListener(HerstellerVerwaltung herstellerVerwaltung) {
    this.herstellerVerwaltung = herstellerVerwaltung;
  }

  @Override
  public void onEvent(CreateHerstellerEvent event) {
    if (this.herstellerVerwaltung.addHersteller(new HerstellerImpl(event.getHerstellerName()))) {
      event.setResponse("SUCCESS");
      return;
    }
    event.setResponse("FAILED");
  }
}

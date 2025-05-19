package listeners;

import domainLogic.HerstellerVerwaltung;
import domainLogic.KuchenAutomat;
import events.DeleteHerstellerEvent;

public class DeleteHerstellerEventListener extends KuchenAutomatEventListener implements
    GenericListener<DeleteHerstellerEvent> {

  private final HerstellerVerwaltung herstellerVerwaltung;

  public DeleteHerstellerEventListener(KuchenAutomat kuchenAutomat,
      HerstellerVerwaltung herstellerVerwaltung) {
    super(kuchenAutomat);
    this.herstellerVerwaltung = herstellerVerwaltung;
  }

  @Override
  public void onEvent(DeleteHerstellerEvent event) {
    if (this.herstellerVerwaltung.deleteHersteller(event.getHersteller())) {
      event.setResponse("SUCCESS");
      return;
    }
    event.setResponse("FAILED");
  }
}

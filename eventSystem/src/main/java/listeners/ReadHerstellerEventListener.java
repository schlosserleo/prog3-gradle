package listeners;

import domainLogic.HerstellerImpl;
import domainLogic.HerstellerVerwaltung;
import domainLogic.KuchenAutomat;
import events.ReadHerstellerEvent;

public class ReadHerstellerEventListener extends KuchenAutomatEventListener implements
    GenericListener<ReadHerstellerEvent> {

  private final HerstellerVerwaltung herstellerVerwaltung;

  public ReadHerstellerEventListener(KuchenAutomat kuchenAutomat,
      HerstellerVerwaltung herstellerVerwaltung) {
    super(kuchenAutomat);
    this.herstellerVerwaltung = herstellerVerwaltung;
  }

  @Override
  public void onEvent(ReadHerstellerEvent event) {
    StringBuilder response = new StringBuilder();
    for (HerstellerImpl hersteller : this.herstellerVerwaltung.getHerstellerListe()) {
      String responseEntry =
          hersteller.getName() + ": " + hersteller.getOwnedCakes(this.getKuchenAutomat()).size()
              + " cakes";

      response.append(responseEntry).append("\n");
    }
    event.setResponse(response.toString().trim());
  }
}

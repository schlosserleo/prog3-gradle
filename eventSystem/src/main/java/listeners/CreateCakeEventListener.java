package listeners;

import domainLogic.KuchenAutomat;
import events.CreateCakeEvent;

public class CreateCakeEventListener extends KuchenAutomatEventListener implements GenericListener<CreateCakeEvent> {

  public CreateCakeEventListener(KuchenAutomat kuchenAutomat) {
    super(kuchenAutomat);
  }

  @Override
  public void onEvent(CreateCakeEvent event) {
    if (this.getKuchenAutomat().create(event.getCakeType(), event.getHersteller(), event.getPreis(),
        event.getNaehrwert(), event.getAllergene(), event.getHaltbarkeit(), event.getObst(),
        event.getKrem())) {
      event.setResponse("SUCCESS");
      return;
    }
    event.setResponse("FAILED");
  }
}


package listeners;

import domainLogic.KuchenAutomat;
import events.ReadCakeEvent;

public class ReadCakeEventListener extends KuchenAutomatEventListener implements GenericListener<ReadCakeEvent> {

  public ReadCakeEventListener(KuchenAutomat kuchenAutomat) {
    super(kuchenAutomat);
  }

  @Override
  public void onEvent(ReadCakeEvent event) {
    if (event.getKuchenSorte() == null) {
      event.setResponse(this.getKuchenAutomat().read().toString());
      return;
    }
    event.setResponse(this.getKuchenAutomat().read(event.getKuchenSorte()).toString());
  }
}
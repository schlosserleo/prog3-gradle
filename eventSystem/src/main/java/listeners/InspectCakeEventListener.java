package listeners;

import domainLogic.KuchenAutomat;
import events.InspectCakeEvent;

public class InspectCakeEventListener extends KuchenAutomatEventListener implements GenericListener<InspectCakeEvent> {
  public InspectCakeEventListener(KuchenAutomat kuchenAutomat) {
    super(kuchenAutomat);
  }

  @Override
  public void onEvent(InspectCakeEvent event) {

   if (this.getKuchenAutomat().update(event.getLocation())) {
     event.setResponse("SUCCESS");
     return;
   }
   event.setResponse("FAILED");
  }
}

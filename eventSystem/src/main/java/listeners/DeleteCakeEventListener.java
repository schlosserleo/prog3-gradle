package listeners;

import domainLogic.KuchenAutomat;
import events.DeleteCakeEvent;

public class DeleteCakeEventListener extends KuchenAutomatEventListener implements
    GenericListener<DeleteCakeEvent> {

  public DeleteCakeEventListener(KuchenAutomat kuchenAutomat) {
    super(kuchenAutomat);
  }

  @Override
  public void onEvent(DeleteCakeEvent event) {
    if (this.getKuchenAutomat().delete(event.getLocation())) {
      event.setResponse("SUCCESS");
      return;
    }
    event.setResponse("FAILURE");
  }
}

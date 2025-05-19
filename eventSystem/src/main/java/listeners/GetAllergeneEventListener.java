package listeners;

import domainLogic.KuchenAutomat;
import events.GetAllergeneEvent;
import java.util.Arrays;

public class GetAllergeneEventListener extends KuchenAutomatEventListener implements
    GenericListener<GetAllergeneEvent> {

  public GetAllergeneEventListener(KuchenAutomat kuchenAutomat) {
    super(kuchenAutomat);
  }

  @Override
  public void onEvent(GetAllergeneEvent event) {
    event.setResponse(Arrays.toString(this.getKuchenAutomat().getAllergene().toArray()));
  }
}

package listeners;

import domainLogic.KuchenAutomat;

public abstract class KuchenAutomatEventListener {
  private final KuchenAutomat kuchenAutomat;
  public KuchenAutomatEventListener(KuchenAutomat kuchenAutomat) {
    this.kuchenAutomat = kuchenAutomat;
  }

  public KuchenAutomat getKuchenAutomat() {
    return kuchenAutomat;
  }
}

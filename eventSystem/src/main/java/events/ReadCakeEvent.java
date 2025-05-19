package events;

import domainLogic.cake.CakeType;

public class ReadCakeEvent extends ResponseEvent {

  private final CakeType cakeType;

  public ReadCakeEvent(Object source, CakeType cakeType) {
    super(source);
    this.cakeType = cakeType;
  }

  public CakeType getKuchenSorte() {
    return this.cakeType;
  }

}

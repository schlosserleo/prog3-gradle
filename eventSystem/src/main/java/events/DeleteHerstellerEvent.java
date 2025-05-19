package events;

import domainLogic.HerstellerImpl;

public class DeleteHerstellerEvent extends ResponseEvent {

  private final HerstellerImpl hersteller;

  public DeleteHerstellerEvent(Object source, HerstellerImpl hersteller) {
    super(source);
    this.hersteller = hersteller;
  }

  public HerstellerImpl getHersteller() {
    return this.hersteller;
  }
}

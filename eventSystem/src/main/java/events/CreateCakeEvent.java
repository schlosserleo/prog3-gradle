package events;

import domainLogic.HerstellerImpl;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import kuchen.Allergen;

public class CreateCakeEvent extends ResponseEvent {

  private final String cakeType;
  private final BigDecimal preis;
  private final HashSet<Allergen> allergene;
  private final HerstellerImpl hersteller;
  private final Duration haltbarkeit;
  private final int naehrwert;

  private final Obst obst;
  private final Krem krem;

  public CreateCakeEvent(Object source, cakeEventBuilder builder) {
    super(source);
    this.cakeType = builder.cakeType;
    this.preis = builder.preis;
    this.allergene = builder.allergene;
    this.naehrwert = builder.naehrwert;
    this.hersteller = builder.hersteller;
    this.haltbarkeit = builder.haltbarkeit;
    this.obst = builder.obst;
    this.krem = builder.krem;
  }

  public String getCakeType() {
    return this.cakeType;
  }

  public HashSet<Allergen> getAllergene() {
    return this.allergene;
  }

  public BigDecimal getPreis() {
    return this.preis;
  }

  public HerstellerImpl getHersteller() {
    return this.hersteller;
  }

  public int getNaehrwert() {
    return this.naehrwert;
  }

  public Duration getHaltbarkeit() {
    return this.haltbarkeit;
  }

  public Obst getObst() {
    return this.obst;
  }

  public Krem getKrem() {
    return this.krem;
  }

  public static class cakeEventBuilder {

    private final String cakeType;
    private final BigDecimal preis;
    private final HashSet<Allergen> allergene;
    private final HerstellerImpl hersteller;
    private final Duration haltbarkeit;
    private final int naehrwert;
    private final Object source;
    private Obst obst;
    private Krem krem;

    public cakeEventBuilder(Object source, String cakeType, BigDecimal preis,
        HashSet<Allergen> allergene, int naehrwert, Duration haltbarkeit, HerstellerImpl hersteller) {
      this.source = source;
      this.cakeType = cakeType;
      this.preis = preis;
      this.allergene = allergene;
      this.naehrwert = naehrwert;
      this.haltbarkeit = haltbarkeit;
      this.hersteller = hersteller;
      this.obst = null;
      this.krem = null;
    }

    public cakeEventBuilder obst(Obst obst) {
      this.obst = obst;
      return this;
    }

    public cakeEventBuilder krem(Krem krem) {
      this.krem = krem;
      return this;
    }

    public CreateCakeEvent build() {
      return new CreateCakeEvent(this.source, this);
    }
  }
}
package events;

import domainLogic.HerstellerImpl;
import domainLogic.cake.CakeType;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;

import kuchen.Allergen;

public class CreateCakeEvent extends ResponseEvent {

  private final CakeType cakeType;
  private final BigDecimal preis;
  private final HashSet<Allergen> allergene;
  private final HerstellerImpl hersteller;
  private final Duration haltbarkeit;
  private final int naehrwert;
  private final Obst obst;
  private final Krem krem;

  public CreateCakeEvent(Object source, CakeEventBuilder builder) {
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

  public CakeType getCakeType() {
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

  public static class CakeEventBuilder {

    private final CakeType cakeType;
    private final BigDecimal preis;
    private final HashSet<Allergen> allergene;
    private final HerstellerImpl hersteller;
    private final Duration haltbarkeit;
    private final int naehrwert;
    private final Object source;
    private Obst obst;
    private Krem krem;

    public CakeEventBuilder(Object source, CakeType cakeType, BigDecimal preis,
        HashSet<Allergen> allergene, int naehrwert, Duration haltbarkeit, HerstellerImpl hersteller) {
      this.source = Objects.requireNonNull(source, "Source cannot be null");
      this.cakeType = Objects.requireNonNull(cakeType, "Cake type cannot be null");
      this.preis = Objects.requireNonNull(preis, "Price cannot be null");
      this.allergene = allergene;
      this.naehrwert = naehrwert;
      this.haltbarkeit = Objects.requireNonNull(haltbarkeit, "Shelf life cannot be null");
      this.hersteller = Objects.requireNonNull(hersteller, "Manufacturer cannot be null");
    }

    public CakeEventBuilder obst(Obst obst) {
      this.obst = Objects.requireNonNull(obst, "Obst component cannot be null");
      return this;
    }

    public CakeEventBuilder krem(Krem krem) {
      this.krem = Objects.requireNonNull(krem, "Krem component cannot be null");
      return this;
    }

    public CreateCakeEvent build() {
      validateComponents();
      return new CreateCakeEvent(this.source, this);
    }

    private void validateComponents() {
      if (cakeType.requiresObst() && obst == null) {
        throw new IllegalStateException(cakeType.getDisplayName() + " requires Obst component");
      }

      if (cakeType.requiresKrem() && krem == null) {
        throw new IllegalStateException(cakeType.getDisplayName() + " requires Krem component");
      }
    }
  }
}

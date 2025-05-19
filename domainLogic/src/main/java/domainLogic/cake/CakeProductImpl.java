package domainLogic.cake;

import domainLogic.KuchenAutomat;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import kuchen.Allergen;
import verwaltung.Hersteller;

public abstract class CakeProductImpl implements CakeProductMutable {

  private final Collection<Allergen> allergene;
  private final int naehrwert;
  private final Date creationDate;
  private final BigDecimal preis;
  private Hersteller hersteller;
  private Date inspektionsdatum;
  private final Duration haltbarkeit;
  private KuchenAutomat kuchenAutomat;

  public CakeProductImpl(BigDecimal preis, int naehrwert, Collection<Allergen> allergene,
      Duration haltbarkeit) {
    this.preis = preis;
    this.naehrwert = naehrwert;
    this.allergene = allergene;
    this.creationDate = Date.from(Instant.now());
    this.hersteller = null;
    this.inspektionsdatum = null;
    this.haltbarkeit = haltbarkeit;
  }

  @Override
  public Hersteller getHersteller() {
    return this.hersteller;
  }

  public void setHersteller(Hersteller hersteller) {
    this.hersteller = hersteller;
  }

  @Override
  public Collection<Allergen> getAllergene() {
    return this.allergene;
  }

  @Override
  public int getNaehrwert() {
    return this.naehrwert;
  }

  @Override
  public Duration getHaltbarkeit() {
    return this.haltbarkeit;
  }

  @Override
  public BigDecimal getPreis() {
    return this.preis;
  }

  @Override
  public Date getInspektionsdatum() {
    return this.inspektionsdatum;
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

  public Duration getRemainingHaltbarkeit() {
    Duration age = Duration.between(creationDate.toInstant(),Instant.now());
    return haltbarkeit.minus(age);
  }

  public void updateInspektionsdatum() {
    this.inspektionsdatum = Date.from(Instant.now());
  }

  public void setKuchenautomat(KuchenAutomat kuchenAutomat) {
    this.kuchenAutomat = kuchenAutomat;
  }

  @Override
  public int getFachnummer() {
    return this.kuchenAutomat.getIndexOfCake(this);
  }
}

package domainLogic.cake;

import domainLogic.cake.parts.Krem;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import kuchen.Allergen;
import kuchen.Kremkuchen;

public class KremkuchenImpl extends CakeProductImpl implements Kremkuchen {

  private final Krem krem;

  public KremkuchenImpl(BigDecimal preis, int naehrwert, Collection<Allergen> allergene,
      Duration haltbarkeit,
      Krem krem) {
    super(preis, naehrwert, allergene, haltbarkeit);
    this.krem = krem;
  }

  @Override
  public String getKremsorte() {
    return this.krem.kremsorte();
  }
}

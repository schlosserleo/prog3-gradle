package domainLogic.cake;

import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;

import kuchen.Allergen;
import verwaltung.Hersteller;

public class KuchenFactory {

  public CakeProductMutable createCake(CakeType cakeType, BigDecimal preis,
      HashSet<Allergen> allergene, Hersteller hersteller, int naehrwerte, Duration haltbarkeit,
      Obst obst, Krem krem) {

    Objects.requireNonNull(cakeType, "Cake type cannot be null");
    Objects.requireNonNull(preis, "Pice cannot be null");
    Objects.requireNonNull(haltbarkeit, "Shelf life cannot be null");

    validateComponents(cakeType, obst, krem);

    return switch (cakeType) {
      case OBSTKUCHEN -> new ObstkuchenImpl(preis, naehrwerte, allergene, haltbarkeit, obst);
      case KREMKUCHEN -> new KremkuchenImpl(preis, naehrwerte, allergene, haltbarkeit, krem);
      case OBSTTORTE -> new ObsttorteImpl(preis, naehrwerte, allergene, haltbarkeit, obst, krem);
    };
  }

  private void validateComponents(CakeType cakeType, Obst obst, Krem krem) {
    if (cakeType.requiresObst() && obst == null) {
      throw new IllegalArgumentException(cakeType.getDisplayName() + " requires Obst component");
    }

    if (cakeType.requiresKrem() && krem == null) {
      throw new IllegalArgumentException(cakeType.getDisplayName() + " requires Krem component");
    }
  }
}

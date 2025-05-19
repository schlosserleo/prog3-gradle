package domainLogic.cake;

import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import kuchen.Allergen;
import verwaltung.Hersteller;

public class KuchenFactory {

  public CakeProductMutable createCake(String cakeType, BigDecimal preis,
      HashSet<Allergen> allergene, Hersteller hersteller, int naehrwerte, Duration haltbarkeit,
      Obst obst, Krem krem) {
    return switch (cakeType) {
      case "Obstkuchen" -> new ObstkuchenImpl(preis, naehrwerte, allergene, haltbarkeit, obst);
      case "Kremkuchen" -> new KremkuchenImpl(preis, naehrwerte, allergene, haltbarkeit, krem);
      case "Obsttorte" -> new ObsttorteImpl(preis, naehrwerte, allergene, haltbarkeit, obst, krem);
      default -> null;
    };
  }
}

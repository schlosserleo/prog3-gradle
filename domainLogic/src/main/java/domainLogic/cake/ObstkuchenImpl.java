package domainLogic.cake;

import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import kuchen.Allergen;
import kuchen.Obstkuchen;

public class ObstkuchenImpl extends CakeProductImpl implements Obstkuchen {

  private final Obst obst;

  public ObstkuchenImpl(BigDecimal preis, int naehrwert, Collection<Allergen> allergene,
      Duration haltbarkeit, Obst obst) {
    super(preis, naehrwert, allergene, haltbarkeit);
    this.obst = obst;
  }

  @Override
  public String getObstsorte() {
    return this.obst.obstsorte();
  }

  @Override
  public CakeType getCakeType() {
    return CakeType.OBSTKUCHEN;
  }
}

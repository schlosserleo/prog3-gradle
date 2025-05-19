package domainLogic;

import domainLogic.cake.CakeProduct;
import java.util.ArrayList;
import verwaltung.Hersteller;

public record HerstellerImpl(String name) implements Hersteller {

  @Override
  public String getName() {
    return this.name;
  }

  public ArrayList<CakeProduct> getOwnedCakes(KuchenAutomat kuchenAutomat) {
    ArrayList<CakeProduct> result = new ArrayList<>();
    for (CakeProduct cake : kuchenAutomat.read()) {
      if (cake.getHersteller().equals(this)) {
        result.add(cake);
      }
    }
    return result;
  }
}

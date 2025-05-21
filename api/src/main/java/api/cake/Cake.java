package api.cake;

import java.time.Duration;
import java.util.Collection;

import api.allergen.Allergen;
import api.manufacturer.Manufacturer;

public interface Cake {
  Manufacturer getManufacturer();

  Collection<Allergen> getAllergens();

  int getNutritionalValue();

  Duration getShelfLife();
}

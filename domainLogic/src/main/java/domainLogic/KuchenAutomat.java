package domainLogic;

import domainLogic.cake.CakeProduct;
import domainLogic.cake.CakeProductMutable;
import domainLogic.cake.CakeType;
import domainLogic.cake.KuchenFactory;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import kuchen.Allergen;
import verwaltung.Verkaufsobjekt;

public class KuchenAutomat {

  private final Map<Verkaufsobjekt, Integer> indexMap;
  private final Map<Integer, CakeProductMutable> kuchenList;

  private final HerstellerVerwaltung herstellerVerwaltung;
  private final KuchenFactory kuchenFactory;
  private final int maxCakes;

  public KuchenAutomat(int maxCakes, HerstellerVerwaltung herstellerVerwaltung) {
    if (maxCakes <= 0) {
      throw new IllegalArgumentException("Maximum cake capacity must be positive");
    }

    this.kuchenList = new HashMap<>();
    this.indexMap = new HashMap<>();
    this.kuchenFactory = new KuchenFactory();
    this.herstellerVerwaltung = Objects.requireNonNull(
        herstellerVerwaltung, "HerstellerVerwaltung cannot be null");
    this.maxCakes = maxCakes;
  }

  public boolean create(CakeType cakeType, HerstellerImpl hersteller, BigDecimal preis, int naehrwert,
      HashSet<Allergen> allergene, Duration haltbarkeit, Obst obst, Krem krem) {
    Objects.requireNonNull(cakeType, "Cake type cannot be null");
    Objects.requireNonNull(hersteller, "Manufacturer cannot be null");
    Objects.requireNonNull(preis, "Price cannot be null");
    Objects.requireNonNull(haltbarkeit, "Shelf life cannot be null");

    try {
      CakeProductMutable kuchen = this.kuchenFactory.createCake(
          cakeType, preis, allergene, naehrwert, haltbarkeit, obst, krem);

      return insert(hersteller, kuchen);
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  public List<CakeProduct> read() {
    return Collections.unmodifiableList(new ArrayList<>(this.kuchenList.values()));
  }

  public CakeProduct read(int fachnummer) {
    return this.kuchenList.get(fachnummer);
  }

  public List<CakeProduct> read(CakeType cakeType) {
    if (cakeType == null) {
      return read();
    }

    ArrayList<CakeProduct> result = new ArrayList<>();
    for (CakeProductMutable cake : this.kuchenList.values()) {
      if (cake.getCakeType() == cakeType) {
        result.add(cake);
      }
    }
    return Collections.unmodifiableList(result);
  }

  public Integer getIndexOfCake(Verkaufsobjekt cake) {
    Objects.requireNonNull(cake, "Cake cannot be null");
    return this.indexMap.get(cake);
  }

  public boolean update(int fachnummer) {
    if (!isFachnummerValid(fachnummer)) {
      return false;
    }
    this.kuchenList.get(fachnummer).updateInspektionsdatum();
    return true;
  }

  public boolean delete(int fachnummer) {
    if (!isFachnummerValid(fachnummer)) {
      return false;
    }
    this.indexMap.remove(this.kuchenList.get(fachnummer));
    this.kuchenList.remove(fachnummer);
    return true;
  }

  public int getCapacity() {
    return maxCakes;
  }

  public int getCurrentCount() {
    return kuchenList.size();
  }

  private boolean isFull() {
    return getCurrentCount() >= maxCakes;
  }

  private boolean isEmpty() {
    return kuchenList.isEmpty();
  }

  public HashSet<Allergen> getAllergene() {
    HashSet<Allergen> result = new HashSet<>();
    for (CakeProduct cake : this.kuchenList.values()) {
      if (cake.getAllergene() != null) {
        result.addAll(cake.getAllergene());
      }
    }
    return result;
  }

  public List<CakeProduct> findCakesWithAlleregn(Allergen allergen) {
    Objects.requireNonNull(allergen, "Allergen cannot be null");

    List<CakeProduct> result = new ArrayList<>();
    for (CakeProduct cake : this.kuchenList.values()) {
      if (cake.getAllergene() != null && cake.getAllergene().contains(allergen)) {
        result.add(cake);
      }
    }
    return Collections.unmodifiableList(result);
  }

  public List<CakeProduct> findCakesByHersteller(HerstellerImpl hersteller) {
    Objects.requireNonNull(hersteller, "Manufacturer cannot be null");

    List<CakeProduct> result = new ArrayList<>();
    for (CakeProduct cake : this.kuchenList.values()) {
      if (cake.getHersteller().equals(hersteller)) {
        result.add(cake);
      }
    }
    return Collections.unmodifiableList(result);
  }

  private boolean insert(HerstellerImpl hersteller, CakeProductMutable kuchen) {
    Objects.requireNonNull(hersteller, "Manufacturer cannot be null");
    Objects.requireNonNull(kuchen, "Cake cannot be null");

    if (!(this.herstellerVerwaltung.containsHersteller(hersteller))) {
      return false;
    }

    Optional<Integer> freeSlotOptional = findNextFreeSlot();
    if (freeSlotOptional.isEmpty()) {
      return false;
    }

    int freeSlot = freeSlotOptional.get();

    kuchen.setKuchenautomat(this);
    kuchen.setHersteller(hersteller);
    kuchen.updateInspektionsdatum();
    this.kuchenList.put(freeSlot, kuchen);
    this.indexMap.put(kuchen, freeSlot);
    return true;
  }

  private boolean isFachnummerValid(int fachnummer) {
    return this.kuchenList.containsKey(fachnummer);
  }

  private Optional<Integer> findNextFreeSlot() {
    if (kuchenList.size() >= maxCakes) {
      return Optional.empty();
    }

    int previousLocation = 0;

    for (int currentLocation : this.kuchenList.keySet()) {
      if (currentLocation - previousLocation > 1) {
        return Optional.of(previousLocation + 1);
      }
      previousLocation = currentLocation;
    }

    if (previousLocation < maxCakes) {
      return Optional.of(previousLocation + 1);
    }

    return Optional.empty();
  }
}

package domainLogic;

import domainLogic.cake.CakeProduct;
import domainLogic.cake.CakeProductMutable;
import domainLogic.cake.KuchenFactory;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import kuchen.Allergen;
import verwaltung.Verkaufsobjekt;

public class KuchenAutomat {

  public final HashMap<Verkaufsobjekt, Integer> indexMap;
  private final HashMap<Integer, CakeProductMutable> kuchenList;
  private final HerstellerVerwaltung herstellerVerwaltung;
  private final KuchenFactory kuchenFactory;
  private final int maxCakes;
  private int nextFreeSlot;

  public KuchenAutomat(int maxCakes, HerstellerVerwaltung herstellerVerwaltung) {
    this.kuchenList = new HashMap<>();
    this.indexMap = new HashMap<>();
    this.kuchenFactory = new KuchenFactory();
    this.herstellerVerwaltung = herstellerVerwaltung;
    this.maxCakes = maxCakes;
    this.nextFreeSlot = 0;
  }

  public boolean create(String cakeType, HerstellerImpl hersteller, BigDecimal preis, int naehrwert,
      HashSet<Allergen> allergene, Duration haltbarkeit, Obst obst, Krem krem) {
    CakeProductMutable kuchen = this.kuchenFactory.createCake(cakeType, preis, allergene, null,
        naehrwert, haltbarkeit, obst, krem);
    return insert(hersteller, kuchen);
  }

  public ArrayList<CakeProduct> read() {
    return new ArrayList<>(this.kuchenList.values());
  }

  public CakeProduct read(int fachnummer) {
    return this.kuchenList.get(fachnummer);
  }

  public ArrayList<CakeProduct> read(Class<?> kuchenSorte) {
    ArrayList<CakeProduct> result = new ArrayList<>();
    for (Map.Entry<Integer, CakeProductMutable> entry : this.kuchenList.entrySet()) {
      if (kuchenSorte.isInstance(entry.getValue())) {
        result.add(entry.getValue());
      }
    }
    return result;
  }

  public Integer getIndexOfCake(Verkaufsobjekt cake) {
    return this.indexMap.get(cake);
  }

  public boolean update(int fachnummer) {
    if (isFachnummerValid(fachnummer)) {
      this.kuchenList.get(fachnummer).updateInspektionsdatum();
      return true;
    }
    return false;
  }

  public boolean delete(int fachnummer) {
    if (isFachnummerValid(fachnummer)) {
      this.indexMap.remove(this.kuchenList.get(fachnummer));
      this.kuchenList.remove(fachnummer);
      return true;
    }
    return false;
  }

  public int getCapacity() {
    return maxCakes;
  }

  private boolean insert(HerstellerImpl hersteller, CakeProductMutable kuchen) {
    if (!(this.herstellerVerwaltung.containsHersteller(hersteller))) {
      return false;
    }
    if (!(findNextFreeSlot())) {
      return false;
    }

    kuchen.setKuchenautomat(this);
    kuchen.setHersteller(hersteller);
    kuchen.updateInspektionsdatum();
    this.kuchenList.put(nextFreeSlot, kuchen);
    this.indexMap.put(kuchen, nextFreeSlot);
    return true;
  }

  public HashSet<Allergen> getAllergene() {
    HashSet<Allergen> result = new HashSet<>();
    for (CakeProduct cake : this.kuchenList.values()) {
      result.addAll(cake.getAllergene());
    }
    return result;
  }

  private boolean isFachnummerValid(int fachnummer) {
    return this.kuchenList.containsKey(fachnummer);
  }

  private boolean findNextFreeSlot() {
    int previousLocation = 0;

    for (int currentLocation : this.kuchenList.keySet()) {
      if (currentLocation - previousLocation > 1) {
        nextFreeSlot = previousLocation + 1;
        return true;
      }
      previousLocation = currentLocation;
    }

    if (previousLocation < maxCakes) {
      nextFreeSlot = previousLocation + 1;
      return true;
    }

    return false;
  }
}

package domainLogic;

import java.util.HashSet;
import verwaltung.Hersteller;

public class HerstellerVerwaltung {

  private final HashSet<HerstellerImpl> herstellerListe;

  public HerstellerVerwaltung() {
    this.herstellerListe = new HashSet<>();
  }

  public boolean addHersteller(HerstellerImpl hersteller) {
    if (containsHersteller(hersteller)) {
      return false;
    }
    this.herstellerListe.add(hersteller);
    return true;
  }

  public Hersteller getHersteller(String name) {
    if (this.containsHersteller(new HerstellerImpl(name))) {
      return new HerstellerImpl(name);
    }
    return null;
  }

  public boolean deleteHersteller(HerstellerImpl hersteller) {
    if (!containsHersteller(hersteller)) {
      return false;
    }
    this.herstellerListe.remove(hersteller);
    return true;
  }

  public boolean containsHersteller(HerstellerImpl hersteller) {
    return herstellerListe.contains(hersteller);
  }

  public HashSet<HerstellerImpl> getHerstellerListe() {
    return new HashSet<>(this.herstellerListe);
  }
}

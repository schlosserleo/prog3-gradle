package domainLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import verwaltung.Hersteller;

class HerstellerVerwaltungTest {

  @org.junit.jupiter.api.Test
  void addHersteller() {
    HashSet<Hersteller> expectedHerstellerListe = new HashSet<>();
    expectedHerstellerListe.add(new HerstellerImpl("Kuchenmeister"));
    expectedHerstellerListe.add(new HerstellerImpl("doktorSchlecker"));

    HerstellerImpl customerOne = new HerstellerImpl("Kuchenmeister");
    HerstellerImpl customerTwo = new HerstellerImpl("doktorSchlecker");
    HerstellerImpl customerThree = new HerstellerImpl("Kuchenmeister");
    HerstellerVerwaltung herstellerVerwaltung = new HerstellerVerwaltung();
    herstellerVerwaltung.addHersteller(customerOne);
    herstellerVerwaltung.addHersteller(customerTwo);
    herstellerVerwaltung.addHersteller(customerThree);
    assertEquals(expectedHerstellerListe, herstellerVerwaltung.getHerstellerListe());
  }
}
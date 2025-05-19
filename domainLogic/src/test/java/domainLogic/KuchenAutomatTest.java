package domainLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import domainLogic.cake.CakeProduct;
import domainLogic.cake.CakeType;
import domainLogic.cake.KremkuchenImpl;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import kuchen.Allergen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KuchenAutomatTest {

  private HerstellerVerwaltung herstellerVerwaltung;
  private HerstellerImpl hersteller;
  private BigDecimal preis;
  private int naehrwert;
  private Krem krem;
  private Obst obst;
  private KuchenAutomat kuchenAutomat;
  private HashSet<Allergen> allergens;
  private Duration standardHaltbarkeit;

  @BeforeEach
  void setUp() {
    herstellerVerwaltung = new HerstellerVerwaltung();
    hersteller = new HerstellerImpl("peter");
    preis = BigDecimal.valueOf(100);
    krem = new Krem("Sahne");
    obst = new Obst("Erdbeere");
    naehrwert = 12;
    herstellerVerwaltung.addHersteller(hersteller);
    kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);
    allergens = new HashSet<>();
    allergens.add(Allergen.Erdnuss);
    standardHaltbarkeit = Duration.ofDays(14);
  }

  @Test
  void createCallsHerstellerVerwaltung() {
    HerstellerVerwaltung mockHerstellerVerwaltung = mock(HerstellerVerwaltung.class);
    when(mockHerstellerVerwaltung.getHersteller("peter")).thenReturn(hersteller);

    KuchenAutomat kuchenAutomat = new KuchenAutomat(100, mockHerstellerVerwaltung);
    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);

    verify(mockHerstellerVerwaltung, times(1)).containsHersteller(hersteller);
  }

  @Test
  void deleteCallsRemoveOnKuchenAutomat() {
    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);

    KuchenAutomat mockedKuchenAutomat = mock(KuchenAutomat.class);
    mockedKuchenAutomat.delete(1);

    verify(mockedKuchenAutomat, times(1)).delete(1);
  }

  @Test
  void createInserts() {
    herstellerVerwaltung = new HerstellerVerwaltung();
    herstellerVerwaltung.addHersteller(hersteller);
    kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);
    assertEquals(1, kuchenAutomat.read().size());
  }

  @Test
  void readReturnsList() {
    herstellerVerwaltung = new HerstellerVerwaltung();
    herstellerVerwaltung.addHersteller(hersteller);
    kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);

    kuchenAutomat.create(CakeType.OBSTKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        obst, null);

    ArrayList<CakeProduct> listToCompare = new ArrayList<>();
    listToCompare.add(kuchenAutomat.read(1));
    listToCompare.add(kuchenAutomat.read(2));

    assertEquals(listToCompare, kuchenAutomat.read());
  }

  @Test
  void deleteRemoves() {
    herstellerVerwaltung = new HerstellerVerwaltung();
    herstellerVerwaltung.addHersteller(hersteller);
    kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);

    kuchenAutomat.delete(1);
    assertEquals(0, kuchenAutomat.read().size());
  }

  @Test
  void updateChangesDate() {
    herstellerVerwaltung = new HerstellerVerwaltung();
    herstellerVerwaltung.addHersteller(hersteller);
    kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);

    Date now = Date.from(Instant.now());
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    kuchenAutomat.update(1);
    assertTrue(now.before(kuchenAutomat.read(1).getInspektionsdatum()));
  }

  @Test
  void testCreateAlleKuchenSorten() {
    herstellerVerwaltung = new HerstellerVerwaltung();
    herstellerVerwaltung.addHersteller(hersteller);
    kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);

    kuchenAutomat.create(CakeType.OBSTKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        obst, null);

    kuchenAutomat.create(CakeType.OBSTTORTE, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        obst, krem);

    assertEquals(3, kuchenAutomat.read().size());
  }

  @Test
  void testReadCakesByClass() {
    herstellerVerwaltung = new HerstellerVerwaltung();
    herstellerVerwaltung.addHersteller(hersteller);
    kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);

    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);

    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        null, krem);

    kuchenAutomat.create(CakeType.OBSTKUCHEN, hersteller, preis, naehrwert, allergens, standardHaltbarkeit,
        obst, null);
    assertEquals(3, kuchenAutomat.read(CakeType.KREMKUCHEN).size());
  }

  @Test
  void testCapacity() {
    kuchenAutomat = new KuchenAutomat(10, herstellerVerwaltung);
    for (int i = 1; i <= 20; i++) {
      kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens,
          standardHaltbarkeit, null, krem);
    }
    assertEquals(10, kuchenAutomat.read().size());
  }

  @Test
  void testRemainingHaltbarkeit() {
    kuchenAutomat.create(CakeType.KREMKUCHEN, hersteller, preis, naehrwert, allergens,
        Duration.ofMillis(10000), null, krem);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    assertTrue(kuchenAutomat.read(1).getRemainingHaltbarkeit().toMillis() <= 9000);
  }
}

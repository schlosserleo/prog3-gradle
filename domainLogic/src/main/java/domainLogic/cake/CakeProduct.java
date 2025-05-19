package domainLogic.cake;

import java.time.Duration;
import java.util.Date;
import kuchen.Kuchen;
import verwaltung.Verkaufsobjekt;

public interface CakeProduct extends Kuchen, Verkaufsobjekt {
  Duration getRemainingHaltbarkeit();
  Date getCreationDate();
}

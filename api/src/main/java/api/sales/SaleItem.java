package api.sales;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SaleItem {
  BigDecimal getPrice();

  LocalDateTime getInspectionDateTime();

  int getCompartmentNumber();
}

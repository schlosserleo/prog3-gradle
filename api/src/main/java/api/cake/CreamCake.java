package api.cake;

import api.sales.SaleItem;

public interface CreamCake extends Cake, SaleItem {
  String getCreamType();
}

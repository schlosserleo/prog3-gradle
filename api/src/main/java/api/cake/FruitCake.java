package api.cake;

import api.sales.SaleItem;

public interface FruitCake extends Cake, SaleItem {
  String getFruitType();
}

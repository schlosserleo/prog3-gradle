package domainLogic.cake;

import domainLogic.KuchenAutomat;
import verwaltung.Hersteller;

public interface CakeProductMutable extends CakeProduct{
    public void updateInspektionsdatum();
    public void setHersteller(Hersteller hersteller);
    public void setKuchenautomat(KuchenAutomat kuchenautomat);
}

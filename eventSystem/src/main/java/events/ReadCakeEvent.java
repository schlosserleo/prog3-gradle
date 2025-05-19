package events;

public class ReadCakeEvent extends ResponseEvent{

  private final Class<?> kuchenSorte;
  public ReadCakeEvent(Object source, Class<?> kuchenSorte) {
    super(source);
    this.kuchenSorte = kuchenSorte;
  }
  public Class<?> getKuchenSorte() {
    return this.kuchenSorte;
  }

}

package api.allergen;

public enum Allergen {
  GLUTEN,
  PEANUT,
  HAZELNUT,
  SESAME_SEEDS;

  public String getDisplayName() {
    return switch (this) {
      case GLUTEN -> "Gluten";
      case PEANUT -> "Peanut";
      case HAZELNUT -> "Hazelnut";
      case SESAME_SEEDS -> "Sesame Seeds";
    };
  }
}

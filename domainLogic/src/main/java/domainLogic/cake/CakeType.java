package domainLogic.cake;

public enum CakeType {
  OBSTKUCHEN("Obstkuchen"),
  KREMKUCHEN("Kremkuchen"),
  OBSTTORTE("Obsttorte");

  private final String displayName;

  CakeType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public static CakeType fromDisplayName(String displayName) {
    for (CakeType type : values()) {
      if (type.displayName.equals(displayName)) {
        return type;
      }
    }
    return null;
  }

  public boolean requiresObst() {
    return this == OBSTKUCHEN || this == OBSTTORTE;
  }

  public boolean requiresKrem() {
    return this == KREMKUCHEN || this == OBSTTORTE;
  }
}

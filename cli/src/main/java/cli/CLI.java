package cli;

import domainLogic.HerstellerImpl;
import domainLogic.cake.CakeType;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import eventDispatcher.EventDispatcher;
import events.CreateCakeEvent;
import events.CreateCakeEvent.CakeEventBuilder;
import events.CreateHerstellerEvent;
import events.DeleteCakeEvent;
import events.DeleteHerstellerEvent;
import events.GetAllergeneEvent;
import events.InspectCakeEvent;
import events.ReadCakeEvent;
import events.ReadHerstellerEvent;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;
import kuchen.Allergen;

public class CLI {

  private static final String CMD_CREATE = ":c";
  private static final String CMD_READ = ":r";
  private static final String CMD_UPDATE = ":u";
  private static final String CMD_DELETE = ":d";
  private static final String CMD_QUIT = ":q";

  private static final String READ_KUCHEN = "kuchen";
  private static final String READ_HERSTELLER = "hersteller";
  private static final String READ_ALLERGENE = "allergene";

  private static final int ARG_CAKE_TYPE = 0;
  private static final int ARG_HERSTELLER = 1;
  private static final int ARG_PREIS = 2;
  private static final int ARG_NAEHRWERT = 3;
  private static final int ARG_HALTBARKEIT = 4;
  private static final int ARG_ALLERGENE = 5;
  private static final int ARG_OBST = 6;
  private static final int ARG_KREM = 7;

  private static final int MIN_ARGS_CAKE_BASE = 6;
  private static final int MIN_ARGS_OBSTKUCHEN = MIN_ARGS_CAKE_BASE + 1;
  private static final int MIN_ARGS_KREMKUCHEN = MIN_ARGS_CAKE_BASE + 1;
  private static final int MIN_ARGS_OBSTTORTE = MIN_ARGS_CAKE_BASE + 2;

  private final String[] launchArgs;
  private final Scanner scanner;
  private final EventDispatcher eventDispatcher;

  public CLI(EventDispatcher eventDispatcher, String[] launchArgs) {
    this.launchArgs = launchArgs;
    this.scanner = new Scanner(System.in);
    this.eventDispatcher = eventDispatcher;
  }

  private void createCake(String[] args) {
    if (args.length < MIN_ARGS_CAKE_BASE) {
      System.out.println("Insufficient arguments for cake creation");
      printCakeCreationHelp();
      return;
    }

    try {
      String cakeTypeStr = args[ARG_CAKE_TYPE];
      CakeType cakeType = CakeType.fromDisplayName(cakeTypeStr);

      if (cakeType == null) {
        System.out.println("Invalid cake type: " + cakeTypeStr);
        printValidCakeTypes();
        return;
      }

      int minRequiredArgs = switch (cakeType) {
        case OBSTKUCHEN -> MIN_ARGS_OBSTKUCHEN;
        case KREMKUCHEN -> MIN_ARGS_KREMKUCHEN;
        case OBSTTORTE -> MIN_ARGS_OBSTTORTE;
      };

      if (args.length < minRequiredArgs) {
        System.out.println("Insufficient arguments for " + cakeType.getDisplayName());
        return;
      }

      HerstellerImpl hersteller = new HerstellerImpl(args[ARG_HERSTELLER]);
      BigDecimal preis = BigDecimal.valueOf(Double.parseDouble(args[ARG_PREIS]));
      int naehrwert = Integer.parseInt(args[ARG_NAEHRWERT]);
      Duration haltbarkeit = Duration.ofDays(Integer.parseInt(args[ARG_HALTBARKEIT]));
      HashSet<Allergen> allergene = parseAllergene(args[ARG_ALLERGENE]);

      CakeEventBuilder cakeEventBuilder = new CakeEventBuilder(this, cakeType, preis, allergene,
          naehrwert, haltbarkeit, hersteller);

      if (cakeType.requiresObst()) {
        cakeEventBuilder.obst(new Obst(args[ARG_OBST]));
      }

      if (cakeType.requiresKrem()) {
        cakeEventBuilder.krem(new Krem(args[ARG_KREM]));
      }

      CreateCakeEvent createCakeEvent = cakeEventBuilder.build();
      this.eventDispatcher.dispatch(createCakeEvent);
      System.out.println(createCakeEvent.getResponse());

    } catch (NumberFormatException e) {
      System.out.println("Invalid number format: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid argument: " + e.getMessage());
    } catch (IllegalStateException e) {
      System.out.println("Error in cake configuration: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("Error creating cake: " + e.getMessage());
    }
  }

  private void printCakeCreationHelp() {
    System.out.println("Cake creation format:");
    System.out.println(
        "<CakeType> <HerstellerName> <Preis> <Naehrwert> <Haltbarkeit> <Allergene> <ObstSorte/KremSorte> [<KremSorte>]");
    System.out.println("Examples:");
    System.out.println("  " + CakeType.OBSTKUCHEN.getDisplayName() +
        " Baker 2.50 300 14 Gluten,Erdnuss Apfel");
    System.out.println("  " + CakeType.KREMKUCHEN.getDisplayName() +
        " Baker 2.50 300 14 Gluten,Erdnuss Sahne");
    System.out.println("  " + CakeType.OBSTTORTE.getDisplayName() +
        " Baker 2.50 300 14 Gluten,Erdnuss Apfel Sahne");
    System.out.println("Available allergens: " +
        Arrays.toString(Allergen.values()));
  }

  private void createHersteller(String herstellerName) {
    if (herstellerName.trim().isEmpty()) {
      System.out.println("Manufacturer cannot be empty");
      return;
    }

    CreateHerstellerEvent createHerstellerEvent = new CreateHerstellerEvent(this, herstellerName);
    this.eventDispatcher.dispatch(createHerstellerEvent);
    System.out.println(createHerstellerEvent.getResponse());
  }

  private void printValidCakeTypes() {
    System.out.println("Valid types are: " + Arrays.stream(CakeType.values())
        .map(CakeType::getDisplayName)
        .collect(Collectors.joining(", ")));
  }

  private HashSet<Allergen> parseAllergene(String allergeneUnparsed) {
    if (allergeneUnparsed.equals(",")) {
      return null;
    }

    try {
      return Arrays.stream(allergeneUnparsed.split(","))
          .map(String::trim)
          .filter(s -> !s.isEmpty())
          .map(Allergen::valueOf)
          .collect(Collectors.toCollection(HashSet::new));
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid allergen. Valid values are: " +
          Arrays.toString(Allergen.values()));
      throw e;
    }
  }

  private String[] getSplittedArgs() {
    return this.scanner.nextLine().split(" ");
  }

  private void createMode() {
    String[] args = getSplittedArgs();
    switch (args.length) {
      case 0 -> System.out.println("Invalid Arguments");
      case 1 -> createHersteller(args[0]);
      default -> createCake(args);
    }
  }

  private void readMode() {
    String[] args = getSplittedArgs();
    if (args.length == 0) {
      System.out.println("No Arguments");
      printReadHelp();
      return;
    }

    switch (args[0]) {
      case READ_KUCHEN -> readCakes(args);
      case READ_HERSTELLER -> readHerstellers();
      case READ_ALLERGENE -> readAllergene();
      default -> {
        System.out.println("Unknown read command: " + args[0]);
        printReadHelp();
      }
    }
  }

  private void printReadHelp() {
    System.out.println("Read commands:");
    System.out.println("  " + READ_KUCHEN + " [type] - Read all cakes or cakes of a specific type");
    System.out.println("  " + READ_HERSTELLER + " - Read all manufacturers");
    System.out.println("  " + READ_ALLERGENE + " - Read all allergens");
  }

  private void readCakes(String[] args) {
    ReadCakeEvent readCakeEvent;
    if (args.length > 1) {
      CakeType cakeType = CakeType.fromDisplayName(args[1]);
      if (cakeType == null) {
        System.out.println("Invalid cake type: " + args[1]);
        printValidCakeTypes();
        return;
      }
      String className = "domainLogic.cake." + cakeType.getDisplayName() + "Impl";
      try {
        readCakeEvent = new ReadCakeEvent(this, Class.forName(className));
      } catch (ClassNotFoundException e) {
        System.out.println("Error: Implementation class not found for " + cakeType.getDisplayName());
        return;
      }
    } else {
      readCakeEvent = new ReadCakeEvent(this, null);
    }

    eventDispatcher.dispatch(readCakeEvent);
    System.out.println(readCakeEvent.getResponse());
  }

  private void readHerstellers() {
    ReadHerstellerEvent readHerstellerEvent = new ReadHerstellerEvent(this);
    eventDispatcher.dispatch(readHerstellerEvent);
    System.out.println(readHerstellerEvent.getResponse());
  }

  private void readAllergene() {
    GetAllergeneEvent getAllergeneEvent = new GetAllergeneEvent(this);
    eventDispatcher.dispatch(getAllergeneEvent);
    System.out.println(getAllergeneEvent.getResponse());
  }

  private void updateMode() {
    String[] args = getSplittedArgs();
    if (args.length == 0) {
      System.out.println("Missing location");
      return;
    }

    try {
      int location = Integer.parseInt(args[0]);
      InspectCakeEvent inspectCakeEvent = new InspectCakeEvent(this, location);
      eventDispatcher.dispatch(inspectCakeEvent);
      System.out.println(inspectCakeEvent.getResponse());
    } catch (NumberFormatException e) {
      System.out.println("Invalid location: " + args[0] + ". Please enter a number.");
    }
  }

  private void deleteMode() {
    String[] args = getSplittedArgs();
    if (args.length == 0) {
      System.out.println("Missing location or Manufacturer name");
      return;
    }

    try {
      int location = Integer.parseInt(args[0]);
      deleteCake(location);
    } catch (NumberFormatException nfe) {
      deleteHersteller(args[0]);
    }
  }

  private void deleteCake(int location) {
    DeleteCakeEvent deleteCakeEvent = new DeleteCakeEvent(this, location);
    this.eventDispatcher.dispatch(deleteCakeEvent);
    System.out.println(deleteCakeEvent.getResponse());
  }

  private void deleteHersteller(String herstellerName) {
    DeleteHerstellerEvent deleteHerstellerEvent = new DeleteHerstellerEvent(
        this, new HerstellerImpl(herstellerName));
    eventDispatcher.dispatch(deleteHerstellerEvent);
    System.out.println(deleteHerstellerEvent.getResponse());
  }

  public void run() {
    while (true) {
      System.out.print("Give Mode ");
      String mode = scanner.nextLine();
      switch (mode) {
        case ":c" -> createMode();
        case ":r" -> readMode();
        case ":u" -> updateMode();
        case ":d" -> deleteMode();
        case ":q" -> {
          return;
        }
      }
    }
  }
}

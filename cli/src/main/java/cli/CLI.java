package cli;

import domainLogic.HerstellerImpl;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import eventDispatcher.EventDispatcher;
import events.CreateCakeEvent;
import events.CreateCakeEvent.cakeEventBuilder;
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

  private final String[] launchArgs;
  private final Scanner scanner;
  private final EventDispatcher eventDispatcher;

  public CLI(EventDispatcher eventDispatcher, String[] launchArgs) {
    this.launchArgs = launchArgs;
    this.scanner = new Scanner(System.in);
    this.eventDispatcher = eventDispatcher;
  }

  private void createCake(String[] cakeArgs) {
    cakeEventBuilder cakeEventBuilder;
    CreateCakeEvent createCakeEvent;

    String cakeType = cakeArgs[0];
    HerstellerImpl hersteller = new HerstellerImpl(cakeArgs[1]);
    BigDecimal preis = BigDecimal.valueOf(Double.parseDouble(cakeArgs[2]));
    int naehrwert = Integer.parseInt(cakeArgs[3]);
    Duration haltbarkeit = Duration.ofDays(Integer.parseInt(cakeArgs[4]));
    HashSet<Allergen> allergene = parseAllergene(cakeArgs[5]);

    cakeEventBuilder = new cakeEventBuilder(this, cakeType, preis, allergene, naehrwert,
        haltbarkeit, hersteller);

    createCakeEvent = switch (cakeType) {
      case "Obstkuchen" -> cakeEventBuilder.obst(new Obst(cakeArgs[6])).build();
      case "Kremkuchen" -> cakeEventBuilder.krem(new Krem(cakeArgs[6])).build();
      case "Obsttorte" ->
          cakeEventBuilder.obst(new Obst(cakeArgs[6])).krem(new Krem(cakeArgs[7])).build();
      default -> throw new IllegalStateException("Unexpected value: " + cakeType);
    };
    this.eventDispatcher.dispatch(createCakeEvent);
    System.out.println(createCakeEvent.getResponse());
  }

  private void createHersteller(String customerName) {
    CreateHerstellerEvent createHerstellerEvent = new CreateHerstellerEvent(this, customerName);
    this.eventDispatcher.dispatch(createHerstellerEvent);
    System.out.println(createHerstellerEvent.getResponse());
  }

  private HashSet<Allergen> parseAllergene(String allergeneUnparsed) {
    if (allergeneUnparsed.equals(",")) {
      return null;
    }
    return Arrays.stream(allergeneUnparsed.split(",")).map(Allergen::valueOf)
        .collect(Collectors.toCollection(HashSet::new));
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

    ReadCakeEvent readCakeEvent;
    if (args[0].equals("kuchen") && args.length > 1) {
      String className = "domainLogic.cake." + args[1] + "Impl";
      try {
        readCakeEvent = new ReadCakeEvent(this, Class.forName(className));
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    } else if (args[0].equals("kuchen")) {
      readCakeEvent = new ReadCakeEvent(this, null);
    } else if (args[0].equals("hersteller")) {
      ReadHerstellerEvent readHerstellerEvent = new ReadHerstellerEvent(this);
      eventDispatcher.dispatch(readHerstellerEvent);
      System.out.println(readHerstellerEvent.getResponse());
      return;
    } else if (args[0].equals("allergene")) {
      GetAllergeneEvent getAllergeneEvent = new GetAllergeneEvent(this);
      eventDispatcher.dispatch(getAllergeneEvent);
      System.out.println(getAllergeneEvent.getResponse());
      return;
    } else {
      System.out.println("No Arguments");
      return;
    }
    eventDispatcher.dispatch(readCakeEvent);
    System.out.println(readCakeEvent.getResponse());
  }

  private void updateMode() {
    String[] args = getSplittedArgs();
    int location = Integer.parseInt(args[0]);
    InspectCakeEvent inspectCakeEvent = new InspectCakeEvent(this, location);
    eventDispatcher.dispatch(inspectCakeEvent);
    System.out.println(inspectCakeEvent.getResponse());
  }

  private void deleteMode() {
    String[] args = getSplittedArgs();
    int location;
    try {
      location = Integer.parseInt(args[0]);
    } catch (NumberFormatException nfe) {
      DeleteHerstellerEvent deleteHerstellerEvent = new DeleteHerstellerEvent(this,
          new HerstellerImpl(args[0]));
      eventDispatcher.dispatch(deleteHerstellerEvent);
      System.out.println(deleteHerstellerEvent.getResponse());
      return;
    }
    DeleteCakeEvent deleteCakeEvent = new DeleteCakeEvent(this, location);
    this.eventDispatcher.dispatch(deleteCakeEvent);
    System.out.println(deleteCakeEvent.getResponse());
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

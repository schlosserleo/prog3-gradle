import cli.CLI;
import domainLogic.HerstellerVerwaltung;
import domainLogic.KuchenAutomat;
import eventDispatcher.EventDispatcher;
import events.CreateCakeEvent;
import events.CreateHerstellerEvent;
import events.DeleteCakeEvent;
import events.DeleteHerstellerEvent;
import events.GetAllergeneEvent;
import events.InspectCakeEvent;
import events.ReadCakeEvent;
import events.ReadHerstellerEvent;
import listeners.CreateCakeEventListener;
import listeners.CreateHerstellerEventListener;
import listeners.DeleteCakeEventListener;
import listeners.DeleteHerstellerEventListener;
import listeners.GetAllergeneEventListener;
import listeners.InspectCakeEventListener;
import listeners.ReadCakeEventListener;
import listeners.ReadHerstellerEventListener;

public class MainCLI {

  public static void main(String[] args) {
    HerstellerVerwaltung hv = new HerstellerVerwaltung();
    KuchenAutomat ka = new KuchenAutomat(3, hv);
    EventDispatcher eventDispatcher = new EventDispatcher();
    CreateCakeEventListener createCakeEventListener = new CreateCakeEventListener(ka);
    ReadCakeEventListener readCakeTypeEventListener = new ReadCakeEventListener(ka);
    InspectCakeEventListener inspectCakeEventListener = new InspectCakeEventListener(ka);
    DeleteCakeEventListener deleteCakeEventListener = new DeleteCakeEventListener(ka);
    CreateHerstellerEventListener createHerstellerEventListener = new CreateHerstellerEventListener(
        hv);
    ReadHerstellerEventListener readHerstellerEventListener = new ReadHerstellerEventListener(ka,
        hv);
    GetAllergeneEventListener getAllergeneEventListener = new GetAllergeneEventListener(ka);
    DeleteHerstellerEventListener deleteHerstellerEventListener = new DeleteHerstellerEventListener(
        ka, hv);

    eventDispatcher.registerListener(CreateCakeEvent.class, createCakeEventListener);
    eventDispatcher.registerListener(ReadCakeEvent.class, readCakeTypeEventListener);
    eventDispatcher.registerListener(InspectCakeEvent.class, inspectCakeEventListener);
    eventDispatcher.registerListener(DeleteCakeEvent.class, deleteCakeEventListener);
    eventDispatcher.registerListener(CreateHerstellerEvent.class, createHerstellerEventListener);
    eventDispatcher.registerListener(ReadHerstellerEvent.class, readHerstellerEventListener);
    eventDispatcher.registerListener(GetAllergeneEvent.class, getAllergeneEventListener);
    eventDispatcher.registerListener(DeleteHerstellerEvent.class, deleteHerstellerEventListener);
    CLI cli = new CLI(eventDispatcher, args);
    cli.run();
  }
}

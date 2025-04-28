
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static List<Job> jobs=new ArrayList<>();
    static List<Resource> resources=new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        //TODO: or not TODO make operating sequence dynamically changeable To buffer or Change first
        //TODO: read from file
        //setBasicData();
        DataReader.readFromFile("src/input.txt",jobs,resources);
        System.out.println("Basic data set: Jobs: "+jobs.size()+", Resources: "+resources.size());
        try {
            new Simulation(LocalTime.of(9,0),jobs,resources).run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setBasicData(){
        Resource mustarDispenser = new Resource("mustarDispenser", 5);
        mustarDispenser.addTimeWindow(new TimeWindow(LocalTime.of(8,0),LocalTime.of(16,0)));
        Resource ketchupDispenser = new Resource("ketchupDispenser", 5);
        ketchupDispenser.addTimeWindow(new TimeWindow(LocalTime.of(8,0),LocalTime.of(16,0)));
        Resource zoldsegAllomas = new Resource("zoldsegAllomas", 3);
        zoldsegAllomas.addTimeWindow(new TimeWindow(LocalTime.of(8,0),LocalTime.of(16,0)));
        Resource husSuto = new Resource("husSuto", 2);
        husSuto.addTimeWindow(new TimeWindow(LocalTime.of(8,0),LocalTime.of(16,0)));
        Resource majonezDispenser = new Resource("majonezDispenser", 5);
        majonezDispenser.addTimeWindow(new TimeWindow(LocalTime.of(8,0),LocalTime.of(16,0)));
        Resource sajtRako = new Resource("sajtRako", 3);
        sajtRako.addTimeWindow(new TimeWindow(LocalTime.of(8,0),LocalTime.of(16,0)));
        Resource kolbaszSuto = new Resource("kolbaszSuto", 2);
        kolbaszSuto.addTimeWindow(new TimeWindow(LocalTime.of(8,0),LocalTime.of(16,0)));
        Resource krumpliSuto = new Resource("krumpliSuto", 2);
        krumpliSuto.addTimeWindow(new TimeWindow(LocalTime.of(8,0),LocalTime.of(16,0)));
        Resource sozoAllomas = new Resource("sozoAllomas", 4);
        sozoAllomas.addTimeWindow(new TimeWindow(LocalTime.of(8,0),LocalTime.of(16,0)));

        OperationType mustar = new OperationType("Mustár", 3);
        mustar.addRequiredResource("mustarDispenser");
        OperationType ketchup = new OperationType("Ketchup", 1);
        ketchup.addRequiredResource("ketchupDispenser");
        OperationType zoldseg = new OperationType("Zöldség rakás", 2);
        zoldseg.addRequiredResource("zoldsegAllomas");
        OperationType hus = new OperationType("Hús sütés", 5);
        hus.addRequiredResource("husSuto");
        OperationType majonez = new OperationType("Majonéz rakás", 1);
        majonez.addRequiredResource("majonezDispenser");
        OperationType sajt = new OperationType("Sajt rakás", 1);
        sajt.addRequiredResource("sajtRako");
        OperationType kolbasz = new OperationType("Kolbász sütés", 5);
        kolbasz.addRequiredResource("husSuto");
        OperationType krumpli = new OperationType("Krumpli sütés", 5);
        krumpli.addRequiredResource("krumpliSuto");
        OperationType sozas = new OperationType("Sózás", 1);
        sozas.addRequiredResource("sozoAllomas");

// Hamburger munka
        Job hamburger = new Job("Hamburger");
        hamburger.addOperation(new JobOperation(mustar,1));
        hamburger.addOperation(new JobOperation(ketchup,2));
        hamburger.addOperation(new JobOperation(zoldseg,3));
        hamburger.addOperation(new JobOperation(hus,4));
        hamburger.addOperation(new JobOperation(majonez,5));
        hamburger.addOperation(new JobOperation(sajt,6));

        Job hamburger2 = new Job("Hamburger");
        hamburger2.addOperation(new JobOperation(mustar,1));
        hamburger2.addOperation(new JobOperation(ketchup,2));
        hamburger2.addOperation(new JobOperation(zoldseg,3));
        hamburger2.addOperation(new JobOperation(hus,4));
        hamburger2.addOperation(new JobOperation(majonez,5));
        hamburger2.addOperation(new JobOperation(sajt,6));

        Job hamburger3 = new Job("Hamburger");
        hamburger3.addOperation(new JobOperation(mustar,1));
        hamburger3.addOperation(new JobOperation(ketchup,2));
        hamburger3.addOperation(new JobOperation(zoldseg,3));
        hamburger3.addOperation(new JobOperation(hus,4));
        hamburger3.addOperation(new JobOperation(majonez,5));
        hamburger3.addOperation(new JobOperation(sajt,6));

        Job hamburger4 = new Job("Hamburger");
        hamburger4.addOperation(new JobOperation(mustar,1));
        hamburger4.addOperation(new JobOperation(ketchup,2));
        hamburger4.addOperation(new JobOperation(zoldseg,3));
        hamburger4.addOperation(new JobOperation(hus,4));
        hamburger4.addOperation(new JobOperation(majonez,5));
        hamburger4.addOperation(new JobOperation(sajt,6));

        Job hamburger5 = new Job("Hamburger");
        hamburger5.addOperation(new JobOperation(mustar,1));
        hamburger5.addOperation(new JobOperation(ketchup,2));
        hamburger5.addOperation(new JobOperation(zoldseg,3));
        hamburger5.addOperation(new JobOperation(hus,4));
        hamburger5.addOperation(new JobOperation(majonez,5));
        hamburger5.addOperation(new JobOperation(sajt,6));

// Hotdog munka
        Job hotdog = new Job("Hotdog");
        hotdog.addOperation(new JobOperation(kolbasz,1));
        hotdog.addOperation(new JobOperation(ketchup,2));
        hotdog.addOperation(new JobOperation(mustar,3));

// Sült krumpli munka
        Job sultKrumpli = new Job("Sült Krumpli");
        sultKrumpli.addOperation(new JobOperation(krumpli,1));
        sultKrumpli.addOperation(new JobOperation(sozas,2));

// Összegyűjtve
        jobs = Arrays.asList(hamburger, hotdog, sultKrumpli,hamburger2,hamburger3,hamburger4,hamburger5);
        resources = Arrays.asList(
                mustarDispenser, ketchupDispenser, zoldsegAllomas, husSuto, majonezDispenser,
                sajtRako, kolbaszSuto, krumpliSuto, sozoAllomas
        );
    }
}
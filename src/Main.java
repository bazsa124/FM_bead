
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static List<Job> jobs=new ArrayList<>();
    static List<Resource> resources=new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        DataReader.readFromFile("src/input2.txt",jobs,resources);
        System.out.println("Basic data set: Jobs: "+jobs.size()+", Resources: "+resources.size());
        try {
            new Simulation(LocalTime.of(9,0),jobs,resources).run();

            Logger.printJobStatistic(jobs);
            Logger.printHistory();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static List<String> history=new ArrayList<>();

    public static void addHistory(String line){
        history.add(line);
    }

    public static void printHistory(){
        System.out.println();
        System.out.println("Simulator history:");
        System.out.println("-------------------------");
        for(String line : history){
            System.out.println("\t"+line);
        }
    }

    public static void printJobStatistic(List<Job> jobs){
        System.out.println("Job statistic");
        System.out.println("-------------------------");
        for (Job job : jobs) {
            System.out.format("\t %-27s %25s %25s %25s %n",
                    "Job: "+job.getName(),
                    "start time: "+job.jobStart(),
                    "esteemed time: "+job.esteemedTime(),
                    "real time: "+job.realTime());
            for (JobOperation operation: job.getOperations()){
                System.out.format("\t\t %-25s %25s %25s %25s %25s %n",
                        "Operation: "+operation.getOperationType().getName(),
                        "started: "+operation.getStartTime(),
                        "ended: "+operation.getEndTime(),
                        "duration: "+operation.getDurationTime(),
                        "in storage: "+operation.getIdleTime());
            }
            System.out.println();
        }
    }
}

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Job {
    private String name;
    private String id;
    private List<JobOperation> operations;
    private JobOperation currentOperation=null;

    public Job(String id, String name) {
        this.name = name;
        this.id=id;
        this.operations = new ArrayList<>();
    }

    public void addOperation(JobOperation operation) {
        operations.add(operation);
    }

    public List<JobOperation> getOperations() {
        return operations;
    }

    public JobOperation getNextPendingOperation() {
        int nextIndex=operations.indexOf(currentOperation)+1;
        if (currentOperation!=operations.getLast()) {
            currentOperation = operations.get(nextIndex);
            //System.out.println("Setting current operation to: " + currentOperation.getOperationType().getName());
            return operations.get(nextIndex);
        }
        else
            return null;
    }

    public void setToPreviousOperation(){
        int previousIndex=operations.indexOf(currentOperation)-1;
        if (currentOperation!=operations.getFirst())
            currentOperation = operations.get(previousIndex);
        else
            currentOperation=null;
    }

    public boolean isJobCompleted() {
        return !operations.getLast().isCompleted();
    }

    public String getName(){
        return name+" id: "+id;
    }

    public boolean isCurrentCompleted(){
        if (currentOperation==null)
                return true;
        else
            return currentOperation.isCompleted();
    }

    public int esteemedTime(){
        int time=0;
        for(JobOperation operation: operations){
            time+=operation.getDurationTime();
        }
        return time;
    }

    public long realTime(){
        return operations.getFirst().getStartTime().until(operations.getLast().getEndTime(),MINUTES);
    }

    public LocalTime jobStart(){
        return operations.getFirst().getStartTime();
    }
}

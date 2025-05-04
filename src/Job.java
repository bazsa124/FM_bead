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

    public JobOperation getNextPendingOperation(boolean set) {
        int nextIndex=operations.indexOf(currentOperation)+1;
        if (currentOperation!=operations.getLast()) {
            if(set)
                currentOperation = operations.get(nextIndex);
            return operations.get(nextIndex);
        }
        else
            return null;
    }

    public void setToNextOperation(int nextIndex){
        currentOperation = operations.get(nextIndex);
    }

    public JobOperation getCurrentOperation(){
        return currentOperation;
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

    public boolean checkForEmptyBuffer(List<Resource> resources){
        if(currentOperation==null || getNextPendingOperation(false)==null)
            return true;
        List<Resource> assignableTo=new ArrayList<>();
        for(Resource r:resources){
            if(r.id.equals(getNextPendingOperation(false).getOperationType().requiredResource)){
                assignableTo.add(r);
            }
        }

        for(Resource r:assignableTo){
            if(!r.getBuffer().isFull() || !r.isBusy)
                return true;
        }
        return false;
    }
}

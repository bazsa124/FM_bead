import java.util.ArrayList;
import java.util.List;

public class Job {
    private String name;
    private List<JobOperation> operations;
    private JobOperation currentOperation=null;

    public Job(String name) {
        this.name = name;
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
            System.out.println("Setting current operation to: " + currentOperation.getOperationType().getName());
            return operations.get(nextIndex);
        }
        else
            return null;
    }

    public boolean isJobCompleted() {
        return !operations.getLast().isCompleted();
    }

    public String getName(){
        return name;
    }

    public boolean isCurrentCompleted(){
        if (currentOperation==null)
                return true;
        else
            return currentOperation.isCompleted();
    }
}

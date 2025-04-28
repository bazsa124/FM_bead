import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Resource {
    String id;
    List<TimeWindow> availableTimes;
    private StorageBuffer buffer;
    boolean isBusy = false;
     JobOperation currentOperation;

    public Resource(String id, int bufferCapacity) {
        this.id = id;
        this.availableTimes = new ArrayList<>();
        this.buffer = new StorageBuffer(bufferCapacity);

        this.isBusy = false;
    }

    public boolean isAvailable(LocalTime currentTime) {
        for (TimeWindow window : availableTimes) {
            if (window.isWithin(currentTime)) {
                return true;
            }
        }
        return false;
    }

    public StorageBuffer getBuffer() {
        return buffer;
    }

    public void addTimeWindow(TimeWindow window) {
        availableTimes.add(window);
    }

    public void assignOperation(JobOperation operation){
        currentOperation=operation;
        isBusy=true;
    }

    public boolean isCurrentOperation(JobOperation operation){
        return currentOperation.equals(operation);
    }

    public void workOneMinute(LocalTime currentTime){
        currentOperation.workOneMinute(currentTime);
        if (currentOperation.isCompleted()){
            JobOperation nextOperation=buffer.poll();
            if(nextOperation==null) {
                isBusy = false;
                currentOperation = null;
            }
            else{
                System.out.println("[" + currentTime + "] Bufferból kiosztva: " + nextOperation.getOperationType().getName());
                nextOperation.setStartTime(currentTime);
                currentOperation=nextOperation;
            }
        }

    }
}

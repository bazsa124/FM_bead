import java.time.LocalTime;
import java.util.*;

public class Resource {
    String id;
    List<TimeWindow> availableTimes;
    private StorageBuffer buffer;
    boolean isBusy;

    int idleTime=0;
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

    public void workOneMinute(LocalTime currentTime, int timeUnit){
        currentOperation.workOneMinute(currentTime,timeUnit);
        buffer.increaseIdleTime();
        if (currentOperation.isCompleted()){
            JobOperation nextOperation=buffer.poll();
            if(nextOperation==null) {
                isBusy = false;
                currentOperation = null;
            }
            else{
                Logger.addHistory("[" + currentTime + "] Bufferból kiosztva: " + nextOperation.getOperationType().getName());
                nextOperation.setStartTime(currentTime);
                currentOperation=nextOperation;
            }
        }
    }

    public static void sortResourcesByBufferSize(List<Resource> resources) {
        Collections.sort(resources, Comparator.comparingInt(r -> r.getBuffer().size()));
    }
}

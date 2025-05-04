import java.time.LocalTime;
import java.util.*;

public class Resource {
    String id;
    List<TimeWindow> availableTimes;
    private StorageBuffer buffer;
    boolean isBusy;

    int idleTime=0;
     Job currentJob;

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

    public void assignOperation(Job operation){
        currentJob=operation;
        isBusy=true;
    }

    public void workOneMinute(LocalTime currentTime, int timeUnit,  List<Resource> resources){
        currentJob.getCurrentOperation().workOneMinute(currentTime,timeUnit);
        buffer.increaseIdleTime();
        if (currentJob.getCurrentOperation().isCompleted() && currentJob.checkForEmptyBuffer(resources)){
            Job nextJob=buffer.poll();
            if(nextJob==null) {
                isBusy = false;
                currentJob = null;
            }
            else{
                Logger.addHistory("[" + currentTime + "] Bufferból kiosztva: " + nextJob.getCurrentOperation().getOperationType().getName());
                nextJob.getCurrentOperation().setStartTime(currentTime);
                currentJob=nextJob;
            }
        }
    }

    public static void sortResourcesByBufferSize(List<Resource> resources) {
        Collections.sort(resources, Comparator.comparingInt(r -> r.getBuffer().size()));
    }
}

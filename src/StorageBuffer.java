import java.util.LinkedList;
import java.util.Queue;

public class StorageBuffer {
    private int capacity;
    private Queue<JobOperation> queue = new LinkedList<>();

    public StorageBuffer(int capacity) {
        this.capacity = capacity;
    }

    public boolean add(JobOperation operation) {
        if (queue.size() < capacity) {
            queue.add(operation);
            return true;
        }
        return false;
    }

    public JobOperation poll() {
        return queue.poll();
    }

    public boolean isFull() {
        return queue.size() >= capacity;
    }

    public boolean doesContain(JobOperation operation){
        return queue.contains(operation);
    }

}

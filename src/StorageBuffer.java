import java.util.LinkedList;
import java.util.Queue;

public class StorageBuffer {
    private int capacity;
    private Queue<Job> queue = new LinkedList<>();

    public StorageBuffer(int capacity) {
        this.capacity = capacity;
    }

    public boolean add(Job job) {
        if (queue.size() < capacity) {
            queue.add(job);
            return true;
        }
        return false;
    }

    public Job poll() {
        return queue.poll();
    }

    public boolean isFull() {
        return queue.size() >= capacity;
    }

    public boolean doesContain(Job job){
        return queue.contains(job);
    }

    public void increaseIdleTime(){
        if(!queue.isEmpty()){
            for(Job job: queue)
                job.getCurrentOperation().increaseIdleTime();
        }
    }

    public int size() {
        return queue.size();
    }

}

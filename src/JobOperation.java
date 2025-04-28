import java.time.LocalTime;

public class JobOperation {
    private OperationType operationType;    // Milyen műveletet kell végrehajtani
    private int sequenceNumber;             // (opcionális) Sorrendi szám a munkán belül
    private int customDuration;
    private boolean completed;              // Készen van-e már a művelet
    private int remainingDuration;          // Maradék idő futás közben
    private LocalTime started;
    private LocalTime ended;

    public JobOperation(OperationType operationType, int sequenceNumber) {
        this.operationType = operationType;
        this.sequenceNumber = sequenceNumber;
        this.customDuration = operationType.duration;
        this.remainingDuration = customDuration;
        this.completed = false;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setStartTime(LocalTime currentTime){started=currentTime;}

    public LocalTime getStartTime(){return started;}

    public LocalTime getEndTime(){return ended;}
    public void workOneMinute(LocalTime currentTime) {
        if (remainingDuration > 0) {
            remainingDuration--;
            System.out.println("[" + currentTime + "] Remaining time:"+remainingDuration+" from operation: " + getOperationType().getName());
        }
        if (remainingDuration == 0) {
            System.out.println("[" + currentTime + "] Operation: " + getOperationType().getName()+" completed");
            completed = true;
            ended=currentTime;
        }
    }
}

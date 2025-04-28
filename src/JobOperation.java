import java.time.LocalTime;

public class JobOperation {
    private OperationType operationType;    // Milyen műveletet kell végrehajtani
    private int sequenceNumber;             // (opcionális) Sorrendi szám a munkán belül
    private int customDuration;
    private boolean completed;              // Készen van-e már a művelet
    private int remainingDuration;          // Maradék idő futás közben
    private LocalTime started;
    private LocalTime ended;

    private int inBufferTime=0;

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

    public int getDurationTime(){
        return customDuration;
    }

    public  void increaseIdleTime(){
        inBufferTime++;
    }

    public int getIdleTime(){
        return inBufferTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setStartTime(LocalTime currentTime){
        if(started==null)started=currentTime;}

    public LocalTime getStartTime(){return started;}

    public LocalTime getEndTime(){return ended;}
    public void workOneMinute(LocalTime currentTime, int timeUnit) {
        if (remainingDuration> 0) {
            remainingDuration-=timeUnit;
            Logger.addHistory("[" + currentTime + "] Remaining time:"+remainingDuration+" from operation: " + getOperationType().getName());
        }
        if (remainingDuration<= 0) {
            remainingDuration=0;
            Logger.addHistory("[" + currentTime + "] Operation: " + getOperationType().getName()+" completed");
            completed = true;
            ended=currentTime;
        }
    }
}

import java.time.LocalTime;
import java.util.List;

import static java.lang.Thread.sleep;

public class Simulation {
    private LocalTime currentTime;
    private List<Job> jobs;
    private List<Resource> resources;
    private int timeStepMinutes = 1; // 1 perces léptetés alapból

    public Simulation(LocalTime startTime, List<Job> jobs, List<Resource> resources) {
        this.currentTime = startTime;
        this.jobs = jobs;
        this.resources = resources;
    }

    public void run() throws InterruptedException {
        while (!allJobsCompleted()) {
            processJobs();
            //processBuffers();
            advanceTime();
            //sleep(1000);
        }
    }

    private void processJobs() {
        for (Job job : jobs) {
            if(job.isCurrentCompleted() && job.isJobCompleted()) {
                System.out.println("[" + currentTime + "] Getting next operation of Job: " + job.getName());
                JobOperation nextOperation = job.getNextPendingOperation();
                if (nextOperation != null) {
                    tryAssignOperation(nextOperation);
                }
            }
        }
    }

    private void tryAssignOperation(JobOperation operation) {
        Resource [] requreidResources = operation.getOperationType().getRequiredResources(resources);
        for(Resource resource : requreidResources){
            if (resource.isAvailable(currentTime) && !resource.isBusy) {
                resource.assignOperation(operation);
                operation.setStartTime(currentTime);
                System.out.println("[" + currentTime + "] Közvetlen kiosztás: " + operation.getOperationType().getName());
                break;
            } else {
                if(!resource.getBuffer().doesContain(operation) && !resource.isCurrentOperation(operation)) {
                    if (!resource.getBuffer().isFull()) {
                        boolean added = resource.getBuffer().add(operation);
                        System.out.println("[" + currentTime + "] Váróterembe rakás: " + operation.getOperationType().getName()+" Success:"+added);

                    } else {
                        System.out.println("[" + currentTime + "] Nincs hely a váróteremben: " + operation.getOperationType().getName());
                    }
                }
            }
        }
    }

    private void advanceTime() {
        currentTime = currentTime.plusMinutes(timeStepMinutes);
        for (Resource resource : resources){
            if(resource.currentOperation!=null && resource.isAvailable(currentTime))
                resource.workOneMinute(currentTime);
        }

    }

    private boolean allJobsCompleted() {
        System.out.println("[" + currentTime + "] Checking completed Jobs");
        // Itt visszaadjuk, hogy minden Job összes Operationje készen van-e
        for (Job job : jobs) {
            if (job.isJobCompleted()) {
                return false;
            }
        }
        printOperationTimes();
        return true;
    }

    private void printOperationTimes(){
        for (Job job : jobs) {
            System.out.println("Job: "+job.getName());
            for (JobOperation operation: job.getOperations()){
                System.out.format("\t %-30s %15s %15s %n","Operation: "+operation.getOperationType().getName(),"started: "+operation.getStartTime(),"ended: "+operation.getEndTime());
            }
            System.out.println();
        }
    }
}


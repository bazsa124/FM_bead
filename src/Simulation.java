import java.time.LocalTime;
import java.util.List;

import static java.lang.Thread.sleep;

public class Simulation {
    private LocalTime currentTime;
    private List<Job> jobs;
    private List<Resource> resources;


    public Simulation(LocalTime startTime, List<Job> jobs, List<Resource> resources) {
        this.currentTime = startTime;
        this.jobs = jobs;
        this.resources = resources;
    }

    public void run() throws InterruptedException {
        int timeStepMinutes = 1;
        while (!allJobsCompleted()) {
            processJobs();
            advanceTime(timeStepMinutes);
            //sleep(1000);
        }
    }

    private void processJobs() {
        for (Job job : jobs) {
            if(job.isCurrentCompleted() && job.isJobCompleted() && job.checkForEmptyBuffer(resources)) {
                Logger.addHistory("[" + currentTime + "] Getting next operation of Job: " + job.getName());
                JobOperation nextOperation = job.getNextPendingOperation(true);
                if (nextOperation != null) {
                    tryAssignOperation(nextOperation,job);
                }
            }
        }
    }

    private void tryAssignOperation(JobOperation operation, Job job) {
        List<Resource> requiredResources = operation.getOperationType().getRequiredResources(resources);
        Resource.sortResourcesByBufferSize(requiredResources);

        for (Resource resource : requiredResources) {
            if (resource.isAvailable(currentTime) && !resource.isBusy) {
                resource.assignOperation(job);
                operation.setStartTime(currentTime);
                Logger.addHistory("[" + currentTime + "] Közvetlen kiosztás: " + operation.getOperationType().getName() + " of " + job.getName());
                return;
            }
        }

        for (Resource resource : requiredResources) {
            if (!resource.getBuffer().isFull()) {
                boolean added = resource.getBuffer().add(job);
                if (added) {
                    operation.setStartTime(currentTime);
                    Logger.addHistory("[" + currentTime + "] Váróterembe rakás: " + operation.getOperationType().getName() + " of " + job.getName());
                    return;
                }
            }
        }

        for (Resource resource : requiredResources) {
            job.setToPreviousOperation();
            Logger.addHistory("[" + currentTime + "] Nincs hely a " + resource.id + " váróteremben: " + operation.getOperationType().getName() + " of " + job.getName());
        }
    }

    private void advanceTime(int timeUnit) {
        currentTime = currentTime.plusMinutes(timeUnit);
        for (Resource resource : resources){
            if(resource.isAvailable(currentTime)) {
                if (resource.currentJob != null)
                    resource.workOneMinute(currentTime,timeUnit,resources);
                else
                    resource.idleTime++;
            }
        }

    }

    private boolean allJobsCompleted() {
        Logger.addHistory("[" + currentTime + "] Checking job completeness");
        // Itt visszaadjuk, hogy minden Job összes Operationje készen van-e
        for (Job job : jobs) {
            if (job.isJobCompleted()) {
                return false;
            }
        }
        return true;
    }
}


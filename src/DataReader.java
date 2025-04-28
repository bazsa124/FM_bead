import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class DataReader {

    public static void readFromFile(String filename,
                                    List<Job> jobs,
                                    List<Resource> resources) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            Map<String, OperationType> operationTypes=new HashMap<>();
            String line;
            String currentSection = "";

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("Resources")) {
                    currentSection = "Resources";
                    continue;
                } else if (line.startsWith("Operations")) {
                    currentSection = "Operations";
                    continue;
                } else if (line.startsWith("Jobs")) {
                    currentSection = "Jobs";
                    continue;
                }

                switch (currentSection) {
                    case "Resources":
                        parseResource(line, resources);
                        break;
                    case "Operations":
                        parseOperation(line, operationTypes);
                        break;
                    case "Jobs":
                        parseJob(line, jobs, operationTypes);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseResource(String line, List<Resource> resources) {
        String[] parts = line.split("\\|", 3);
        String name = parts[0];
        int bufferSize = Integer.parseInt(parts[1]);
        Resource resource = new Resource(name, bufferSize);

        String windowsPart = parts[2].replaceAll("[{}\\[\\]]", ""); // kiveszi { } [ ] jeleket
        String[] windowStrings = windowsPart.split(";");

        for (String window : windowStrings) {
            String[] times = window.split(",");
            LocalTime start = LocalTime.parse(times[0]);
            LocalTime end = LocalTime.parse(times[1]);
            resource.addTimeWindow(new TimeWindow(start, end));
        }

        resources.add(resource);
    }

    private static void parseOperation(String line, Map<String, OperationType> operationTypes) {
        String[] parts = line.split("\\|");
        String name = parts[0];
        int duration = Integer.parseInt(parts[1]);
        String requiredResourceId = parts[2];

        OperationType opType = new OperationType(name, duration);
        opType.addRequiredResource(requiredResourceId);
        operationTypes.put(name, opType);
    }

    private static void parseJob(String line, List<Job> jobs, Map<String, OperationType> operationTypes) {
        String[] parts = line.split("\\|", 2);
        String jobName = parts[0];
        String id=parts[1];
        Job job = new Job(id,jobName);

        String opsPart = parts[2].replaceAll("[{}\\[\\]]", "");
        String[] operations = opsPart.split(";");

        for (String opDef : operations) {
            String[] opParts = opDef.split(",");
            String operationName = opParts[0].trim();
            int sequenceNumber = Integer.parseInt(opParts[1].trim());

            OperationType opType = operationTypes.get(operationName);
            if (opType == null) {
                System.err.println("Ismeretlen OperationType: " + operationName);
                continue;
            }
            job.addOperation(new JobOperation(opType, sequenceNumber));
        }

        jobs.add(job);
    }
}

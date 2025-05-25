import java.io.*;
import java.util.*;
import model.GuestData;
import model.UsageRecord;

public class Processor {

    private static final double RATE_4G = 0.05;          // per KB
    private static final double RATE_5G = 0.10;          // per KB
    private static final double ROAM_4G_EXTRA = 0.10;    // 10% extra for roaming 4G
    private static final double ROAM_5G_EXTRA = 0.15;    // 15% extra for roaming 5G
    private static final double EXTRA_CHARGE = 0.05;     // 5% extra if threshold crossed
    private static final int THRESHOLD_KB = 50000;       // example threshold

    private Map<String, GuestData> userDataMap = new HashMap<>();

    // Parse one line into UsageRecord object
    public UsageRecord parseLine(String line) throws IllegalArgumentException {
        String[] parts = line.split("\\|");
        if(parts.length != 5) {
            throw new IllegalArgumentException("Invalid input line: " + line);
        }

        String mobile = parts[0].trim();
        String tower = parts[1].trim();
        int d4g = Integer.parseInt(parts[2].trim());
        int d5g = Integer.parseInt(parts[3].trim());
        boolean roaming = parts[4].trim().equalsIgnoreCase("Yes");

        if(mobile.length() != 10) {
            throw new IllegalArgumentException("Invalid mobile number: " + mobile);
        }

        return new UsageRecord(mobile, tower, d4g, d5g, roaming);
    }

    // Add record to map and aggregate
    public void addRecord(UsageRecord record) {
        userDataMap.putIfAbsent(record.getMobileNumber(), new GuestData());
        userDataMap.get(record.getMobileNumber()).addUsage(record);
    }

    // Process input files
    public void processFiles(List<String> filenames) {
        for(String filename : filenames) {
            try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                while((line = br.readLine()) != null) {
                    try {
                        UsageRecord record = parseLine(line);
                        addRecord(record);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Skipping line due to error: " + e.getMessage());
                    }
                }
            } catch(IOException e) {
                System.err.println("Error reading file " + filename + ": " + e.getMessage());
            }
        }
    }

    // Calculate cost for one user
    public double calculateCost(GuestData data) {
        int d4g = data.getData4GHome();
        int d5g = data.getData5GHome();
        int d4gr = data.getData4GRoaming();
        int d5gr = data.getData5GRoaming();

        double cost =
                d4g * RATE_4G +
                d5g * RATE_5G +
                d4gr * RATE_4G * (1 + ROAM_4G_EXTRA) +
                d5gr * RATE_5G * (1 + ROAM_5G_EXTRA);

        int totalData = d4g + d5g + d4gr + d5gr;
        if (totalData > THRESHOLD_KB) {
            cost += cost * EXTRA_CHARGE;
        }
        return cost;
    }

    // Print final report to console
    public void printReport() {
        System.out.println("Mobile Number|4G|5G|4G Roaming|5G Roaming|Cost");
        for (Map.Entry<String, GuestData> entry : userDataMap.entrySet()) {
            String mobile = entry.getKey();
            GuestData data = entry.getValue();

            double cost = calculateCost(data);
            System.out.printf("%s|%d|%d|%d|%d|%.2f%n",
                    mobile,
                    data.getData4GHome(),
                    data.getData5GHome(),
                    data.getData4GRoaming(),
                    data.getData5GRoaming(),
                    cost);
        }
    }

    public Map<String, GuestData> getUserDataMap() {
        return userDataMap;
    }
    
}

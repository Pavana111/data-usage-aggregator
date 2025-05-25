package model;

// Represents one usage record for a mobile number
public class UsageRecord {
    private String mobileNumber; // user's mobile number
    private String tower;        // tower location
    private int data4G;          // 4G data used
    private int data5G;          // 5G data used
    private boolean roaming;     // is user roaming?

    // Constructor to initialize all fields
    public UsageRecord(String mobileNumber, String tower, int data4G, int data5G, boolean roaming) {
        this.mobileNumber = mobileNumber;
        this.tower = tower;
        this.data4G = data4G;
        this.data5G = data5G;
        this.roaming = roaming;
    }

    public String getMobileNumber() { return mobileNumber; }
    public int getData4G() { return data4G; }
    public int getData5G() { return data5G; }
    public boolean isRoaming() { return roaming; }
}

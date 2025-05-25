package model;

// Stores total 4G and 5G usage data, split by home and roaming
public class GuestData {
    private int data4GHome = 0;       // Total 4G data used at home
    private int data5GHome = 0;       // Total 5G data used at home
    private int data4GRoaming = 0;    // Total 4G data used while roaming
    private int data5GRoaming = 0;    // Total 5G data used while roaming

    // Adds usage from a single record to the totals
    public void addUsage(UsageRecord record) {
        if (record.isRoaming()) {
            data4GRoaming += record.getData4G(); // Add to roaming if user is roaming
            data5GRoaming += record.getData5G();
        } else {
            data4GHome += record.getData4G();    // Add to home if not roaming
            data5GHome += record.getData5G();
        }
    }

    // Getters for all usage data
    public int getData4GHome() { return data4GHome; }
    public int getData5GHome() { return data5GHome; }
    public int getData4GRoaming() { return data4GRoaming; }
    public int getData5GRoaming() { return data5GRoaming; }
}

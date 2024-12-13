public class Vehicle {
    private String plateNumber;
    private String type;
    private String status; // PARKED, RESERVED
    private String entryTime;

    public Vehicle(String plateNumber, String type, String status, String entryTime) {
        this.plateNumber = plateNumber;
        this.type = type;
        this.status = status;
        this.entryTime = entryTime;
    }

    // Getters and setters
    public String getPlateNumber() { return plateNumber; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getEntryTime() { return entryTime; }
    public void setStatus(String status) { this.status = status; }
} 
public class ParkingRecord {
    private String plateNumber;
    private String vehicleType;
    private String entryTime;
    private String exitTime;
    private double fee;
    private String status;

    public ParkingRecord(String plateNumber, String vehicleType, String entryTime, 
                        String exitTime, double fee, String status) {
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
        this.status = status;
    }

    // Getters
    public String getPlateNumber() { return plateNumber; }
    public String getVehicleType() { return vehicleType; }
    public String getEntryTime() { return entryTime; }
    public String getExitTime() { return exitTime; }
    public double getFee() { return fee; }
    public String getStatus() { return status; }
} 
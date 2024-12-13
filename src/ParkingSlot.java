public class ParkingSlot {
    private int slotNumber;
    private String status; // AVAILABLE, PARKED, RESERVED
    private Vehicle vehicle;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.status = "AVAILABLE";
        this.vehicle = null;
    }

    public int getSlotNumber() { return slotNumber; }
    public String getStatus() { return status; }
    public Vehicle getVehicle() { return vehicle; }

    public void setStatus(String status) { this.status = status; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
} 
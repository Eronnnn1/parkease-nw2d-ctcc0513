import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingSystem {
    private static Map<String, Double> vehicleFees = new HashMap<>();
    private static Map<String, Double> initialFees = new HashMap<>();
    private static List<Vehicle> parkedVehicles = new ArrayList<>();
    private static List<ParkingRecord> parkingHistory = new ArrayList<>();
    private static double totalRevenue = 0;
    private static ParkingSlot[][] parkingMap = new ParkingSlot[5][6]; // 5 rows, 6 columns

    static {
        // Initialize hourly parking fees
        vehicleFees.put("Car", 50.0);
        vehicleFees.put("Motorcycle", 30.0);
        vehicleFees.put("Truck", 100.0);

        // Initialize initial parking fees
        initialFees.put("Car", 30.0);
        initialFees.put("Motorcycle", 20.0);
        initialFees.put("Truck", 50.0);

        // Initialize parking slots
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 6; col++) {
                parkingMap[row][col] = new ParkingSlot(row * 6 + col + 1);
            }
        }
    }

    public static boolean parkVehicle(String plateNumber, String type, int slotNumber) {
        ParkingSlot slot = findSlotByNumber(slotNumber);
        if (slot != null && slot.getStatus().equals("AVAILABLE")) {
            String currentTime = getCurrentTime();
            Vehicle vehicle = new Vehicle(plateNumber, type, "PARKED", currentTime);
            slot.setVehicle(vehicle);
            slot.setStatus("PARKED");
            parkedVehicles.add(vehicle);
            
            // Add initial fee to total revenue
            double initialFee = initialFees.getOrDefault(type, 0.0);
            totalRevenue += initialFee;
            
            // Add to parking history with initial fee
            addToParkingHistory(vehicle, null, "PARKED", initialFee);
            return true;
        }
        return false;
    }

    public static boolean reserveParking(String plateNumber, String type, int slotNumber) {
        ParkingSlot slot = findSlotByNumber(slotNumber);
        if (slot != null && slot.getStatus().equals("AVAILABLE")) {
            String currentTime = getCurrentTime();
            Vehicle vehicle = new Vehicle(plateNumber, type, "RESERVED", currentTime);
            slot.setVehicle(vehicle);
            slot.setStatus("RESERVED");
            parkedVehicles.add(vehicle);
            addToParkingHistory(vehicle, null, "RESERVED", 0.0);
            return true;
        }
        return false;
    }

    public static double releaseVehicle(String plateNumber) {
        Vehicle vehicle = findVehicle(plateNumber);
        ParkingSlot slot = findSlotByVehicle(plateNumber);
        if (vehicle != null && slot != null) {
            double fee = calculateFee(vehicle);
            totalRevenue += fee;
            parkedVehicles.remove(vehicle);
            slot.setVehicle(null);
            slot.setStatus("AVAILABLE");
            addToParkingHistory(vehicle, getCurrentTime(), "RELEASED", 0.0);
            return fee;
        }
        return 0;
    }

    private static void addToParkingHistory(Vehicle vehicle, String exitTime, String status, Double initialFee) {
        double fee = 0.0;
        if (status.equals("RELEASED")) {
            fee = calculateFee(vehicle);
        } else if (status.equals("PARKED")) {
            fee = initialFee;
        }

        ParkingRecord record = new ParkingRecord(
            vehicle.getPlateNumber(),
            vehicle.getType(),
            vehicle.getEntryTime(),
            exitTime,
            fee,
            status
        );
        parkingHistory.add(record);
    }

    private static Vehicle findVehicle(String plateNumber) {
        return parkedVehicles.stream()
            .filter(v -> v.getPlateNumber().equals(plateNumber))
            .findFirst()
            .orElse(null);
    }

    private static double calculateFee(Vehicle vehicle) {
        return vehicleFees.getOrDefault(vehicle.getType(), 0.0);
    }

    private static String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private static ParkingSlot findSlotByNumber(int slotNumber) {
        int row = (slotNumber - 1) / 6;
        int col = (slotNumber - 1) % 6;
        if (row >= 0 && row < 5 && col >= 0 && col < 6) {
            return parkingMap[row][col];
        }
        return null;
    }

    private static ParkingSlot findSlotByVehicle(String plateNumber) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 6; col++) {
                ParkingSlot slot = parkingMap[row][col];
                if (slot.getVehicle() != null && 
                    slot.getVehicle().getPlateNumber().equals(plateNumber)) {
                    return slot;
                }
            }
        }
        return null;
    }

    public static ParkingSlot[][] getParkingMap() {
        return parkingMap;
    }

    // Getters for dashboard
    public static List<ParkingRecord> getParkingHistory() { return parkingHistory; }
    public static double getTotalRevenue() { return totalRevenue; }
    public static Map<String, Double> getVehicleFees() { return vehicleFees; }
    public static Map<String, Double> getInitialFees() {
        return initialFees;
    }
} 
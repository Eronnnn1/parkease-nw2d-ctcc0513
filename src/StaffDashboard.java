import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class StaffDashboard extends JFrame {
    private JTable parkingTable;
    private DefaultTableModel tableModel;
    private ParkingMapPanel parkingMapPanel;

    public StaffDashboard() {
        setTitle("Staff Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton parkButton = new JButton("Park Vehicle");
        JButton reserveButton = new JButton("Reserve Parking");
        JButton releaseButton = new JButton("Release Vehicle");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(parkButton);
        buttonPanel.add(reserveButton);
        buttonPanel.add(releaseButton);
        buttonPanel.add(logoutButton);

        // Create parking map panel
        parkingMapPanel = new ParkingMapPanel();
        JPanel mapContainer = new JPanel(new BorderLayout());
        mapContainer.add(parkingMapPanel, BorderLayout.CENTER);
        mapContainer.add(new JLabel("Parking Map", SwingConstants.CENTER), BorderLayout.NORTH);

        // Create table
        String[] columns = {"Plate Number", "Vehicle Type", "Entry Time", "Exit Time", "Fee", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        parkingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(parkingTable);

        // Split the main panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            mapContainer, scrollPane);
        splitPane.setDividerLocation(300);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Add action listeners
        parkButton.addActionListener(e -> showParkDialog());
        reserveButton.addActionListener(e -> showReserveDialog());
        releaseButton.addActionListener(e -> showReleaseDialog());
        logoutButton.addActionListener(e -> logout());

        add(mainPanel);
        refreshTable();
    }

    private void showParkDialog() {
        JTextField plateField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Car", "Motorcycle", "Truck"});
        JTextField slotField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Plate Number:"));
        panel.add(plateField);
        panel.add(new JLabel("Vehicle Type:"));
        panel.add(typeBox);
        panel.add(new JLabel("Slot Number (1-30):"));
        panel.add(slotField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Park Vehicle",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String plateNumber = plateField.getText();
                String type = (String) typeBox.getSelectedItem();
                int slotNumber = Integer.parseInt(slotField.getText());
                
                if (ParkingSystem.parkVehicle(plateNumber, type, slotNumber)) {
                    JOptionPane.showMessageDialog(this, "Vehicle parked successfully!");
                    refreshTable();
                    parkingMapPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Slot is not available!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid slot number!");
            }
        }
    }

    private void showReserveDialog() {
        JTextField plateField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Car", "Motorcycle", "Truck"});
        JTextField slotField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Plate Number:"));
        panel.add(plateField);
        panel.add(new JLabel("Vehicle Type:"));
        panel.add(typeBox);
        panel.add(new JLabel("Slot Number (1-30):"));
        panel.add(slotField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Reserve Parking",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String plateNumber = plateField.getText();
                String type = (String) typeBox.getSelectedItem();
                int slotNumber = Integer.parseInt(slotField.getText());
                
                if (ParkingSystem.reserveParking(plateNumber, type, slotNumber)) {
                    JOptionPane.showMessageDialog(this, "Parking reserved successfully!");
                    refreshTable();
                    parkingMapPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Slot is not available!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid slot number!");
            }
        }
    }

    private void showReleaseDialog() {
        String plateNumber = JOptionPane.showInputDialog(this, "Enter plate number:");
        if (plateNumber != null && !plateNumber.isEmpty()) {
            double fee = ParkingSystem.releaseVehicle(plateNumber);
            if (fee > 0) {
                JOptionPane.showMessageDialog(this, 
                    String.format("Vehicle released. Fee: ₱%.2f", fee));
                refreshTable();
                parkingMapPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Vehicle not found!");
            }
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (ParkingRecord record : ParkingSystem.getParkingHistory()) {
            tableModel.addRow(new Object[]{
                record.getPlateNumber(),
                record.getVehicleType(),
                record.getEntryTime(),
                record.getExitTime() != null ? record.getExitTime() : "-",
                String.format("₱%.2f", record.getFee()),
                record.getStatus()
            });
        }
    }

    private void logout() {
        new LoginForm().setVisible(true);
        this.dispose();
    }
} 
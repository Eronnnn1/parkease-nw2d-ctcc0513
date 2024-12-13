import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ManagerDashboard extends JFrame {
    private JTable parkingTable;
    private DefaultTableModel tableModel;
    private JLabel revenueLabel;

    public ManagerDashboard() {
        setTitle("Manager Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create top panel for revenue
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        revenueLabel = new JLabel("Total Revenue: ₱0.00");
        JButton logoutButton = new JButton("Logout");
        topPanel.add(revenueLabel);
        topPanel.add(logoutButton);

        // Create table
        String[] columns = {"Plate Number", "Vehicle Type", "Entry Time", "Exit Time", "Fee", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        parkingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(parkingTable);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        logoutButton.addActionListener(e -> logout());

        add(mainPanel);
        refreshData();
    }

    private void refreshData() {
        // Update revenue
        revenueLabel.setText(String.format("Total Revenue: ₱%.2f", ParkingSystem.getTotalRevenue()));

        // Update table
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
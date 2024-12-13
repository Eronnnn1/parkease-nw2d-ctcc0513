import javax.swing.*;
import java.awt.*;

public class ParkingMapPanel extends JPanel {
    private static final int SLOT_SIZE = 80;
    private static final Color AVAILABLE_COLOR = new Color(144, 238, 144); // Light green
    private static final Color PARKED_COLOR = new Color(255, 99, 71);     // Tomato red
    private static final Color RESERVED_COLOR = new Color(255, 218, 185);  // Peach

    public ParkingMapPanel() {
        setPreferredSize(new Dimension(6 * SLOT_SIZE, 5 * SLOT_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ParkingSlot[][] parkingMap = ParkingSystem.getParkingMap();
        
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 6; col++) {
                ParkingSlot slot = parkingMap[row][col];
                int x = col * SLOT_SIZE;
                int y = row * SLOT_SIZE;

                // Draw slot background
                switch (slot.getStatus()) {
                    case "AVAILABLE":
                        g2d.setColor(AVAILABLE_COLOR);
                        break;
                    case "PARKED":
                        g2d.setColor(PARKED_COLOR);
                        break;
                    case "RESERVED":
                        g2d.setColor(RESERVED_COLOR);
                        break;
                }
                g2d.fillRect(x, y, SLOT_SIZE - 1, SLOT_SIZE - 1);

                // Draw slot border
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, SLOT_SIZE - 1, SLOT_SIZE - 1);

                // Prepare text to display
                StringBuilder slotText = new StringBuilder();
                slotText.append("Slot ").append(slot.getSlotNumber()).append("\n");
                slotText.append("(").append(slot.getStatus()).append(")");

                // Add vehicle info if occupied
                if (!slot.getStatus().equals("AVAILABLE")) {
                    Vehicle vehicle = slot.getVehicle();
                    slotText.append("\n").append(vehicle.getPlateNumber());
                    slotText.append("\n").append(vehicle.getType());
                }

                // Draw text with smaller font for status
                Font originalFont = g2d.getFont();
                Font smallerFont = originalFont.deriveFont(originalFont.getSize() * 0.9f);
                g2d.setFont(smallerFont);
                
                // Draw multi-line text
                drawCenteredString(g2d, slotText.toString(), x, y, SLOT_SIZE, SLOT_SIZE);
                
                // Restore original font
                g2d.setFont(originalFont);
            }
        }
    }

    private void drawCenteredString(Graphics2D g2d, String text, int x, int y, int width, int height) {
        FontMetrics fm = g2d.getFontMetrics();
        String[] lines = text.split("\n");
        int lineHeight = fm.getHeight();
        int totalHeight = lines.length * lineHeight;
        int startY = y + (height - totalHeight) / 2 + fm.getAscent();

        for (String line : lines) {
            int stringWidth = fm.stringWidth(line);
            int startX = x + (width - stringWidth) / 2;
            g2d.drawString(line, startX, startY);
            startY += lineHeight;
        }
    }
} 
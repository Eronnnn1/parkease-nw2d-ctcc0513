import javax.swing.*;

public class Main {
    private static User[] users = {
        new User("staff", "123", "STAFF"),
        new User("admin", "123", "ADMIN")
    };

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }

    public static User[] getUsers() {
        return users;
    }
}
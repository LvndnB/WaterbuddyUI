import ui.Webinterface;
import javax.swing.*;
import java.sql.*;


public class Main {
    public Main() throws SQLException {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });





    }


    private static void createGUI() {
        Webinterface ui = new Webinterface();
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



}





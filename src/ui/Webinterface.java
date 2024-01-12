package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Webinterface {
    private JPanel rootPanel;
    private JTable Kranentabel;

    public JPanel getRootPanel(){
        return rootPanel;
    }

    public Webinterface() {
        createTable();
    }

    private void createTable(){

        List<Plant> plants = getPlantsFromDatabase();
        Object[][] data = new Object[plants.size()][7];

        for (int i = 0; i < plants.size(); i++) {
            Plant plant = plants.get(i);
            data[i][0] = plant.getKraannummer();
            data[i][1] = plant.getPlantnaam();
            data[i][2] = plant.getPlantsoort();
            data[i][3] = plant.getMinbv() +"%";
            data[i][4] = plant.getMaxbv() +"%";
            data[i][5] = plant.getHuidigebv() +"%";
            data[i][6] = plant.getStatus();
        }

        Kranentabel.setModel(new DefaultTableModel(
                data,
                new String[] {"KraanNummer", "Plantnaam", "Plantsoort", "MinBV", "MaxBV", "HuidigeBV", "Status (0 = Idle, 1 = Watergeven)"}
        ));
    }

    private static List<Plant> getPlantsFromDatabase() {
        List<Plant> plants = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/waterbuddydb";
        String gebruikersnaam = "root";
        String wachtwoord = "welkom01";

        try (Connection connection = DriverManager.getConnection(url, gebruikersnaam, wachtwoord)) {
            String query = "SELECT  k.Kraannummer, k.plantnaam, k.plantsoort, p.minbv, p.maxbv, k.huidigebv, k.status FROM kranentabel k JOIN planten p ON k.plantsoort = p.plantsoort";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int kraannummer = resultSet.getInt("Kraannummer");
                    String plantnaam = resultSet.getString("plantnaam");
                    String plantsoort = resultSet.getString("plantsoort");
                    int minbv = resultSet.getInt("minbv");
                    int maxbv = resultSet.getInt("maxbv");
                    int huidigebv = resultSet.getInt("huidigebv");
                    String status = resultSet.getString("status");

                    Plant plant = new Plant(kraannummer, plantnaam, plantsoort, minbv, maxbv, huidigebv, status);
                    plants.add(plant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plants;
    }


    public static java.util.List<Plant> getPlantsForTable() {
        return getPlantsFromDatabase();
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

class Plant {
    private int kraannummer;
    private String plantnaam;
    private String plantsoort;
    private int minbv;
    private int maxbv;
    private int huidigebv;
    private String status;

    public Plant(int kraannummer, String plantnaam, String plantsoort, int minbv, int maxbv, int huidigebv, String status) {
        this.kraannummer = kraannummer;
        this.plantnaam = plantnaam;
        this.plantsoort = plantsoort;
        this.minbv = minbv;
        this.maxbv = maxbv;
        this.huidigebv = huidigebv;
        this.status = status;
    }

    public int getKraannummer() {
        return kraannummer;
    }

    public String getPlantnaam() {
        return plantnaam;
    }

    public String getPlantsoort() {
        return plantsoort;
    }

    public int getMinbv() {
        return minbv;
    }

    public int getMaxbv() {
        return maxbv;
    }

    public int getHuidigebv() {
        return huidigebv;
    }

    public String getStatus() {
        return status;
    }
}




import java.util.ArrayList;
import java.sql.*;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class Frame {

    private JComboBox<String> dropdown1, dropdown2;

    public void createFrame() throws ClassNotFoundException {
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        

        dropdown1 = new JComboBox<>();
        dropdown1.setBounds(0,0,400,50);
        frame.add(dropdown1);

        dropdown2 = new JComboBox<>();
        dropdown2.setBounds(0,50,400,50);
        frame.add(dropdown2);

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Currency"; // Ihre PostgreSQL-Datenbank-URL
            String user = "lucas"; // Ihr PostgreSQL-Benutzername
            String password = ""; // Ihr PostgreSQL-Passwort
            Connection con = DriverManager.getConnection(url, user, password);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select currency, shortcut from currencys");

            ArrayList<String> fromDataList = new ArrayList<>();
            ArrayList<String> toDataList = new ArrayList<>();
            while (rs.next()) {
                String currency = rs.getString("currency");
                String shortcut = rs.getString("shortcut");
                fromDataList.add(currency + " - " + shortcut);
                toDataList.add(currency + " - " + shortcut);
            }

            for (String currency : fromDataList) {
                dropdown1.addItem(currency);
            }
            for (String currency : toDataList) {
                dropdown2.addItem(currency);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }
}

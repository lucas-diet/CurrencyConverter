import java.util.ArrayList;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Frame {

    private JComboBox<String> dropdown1, dropdown2;

    public void createFrame() throws ClassNotFoundException {
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        dropdown1 = new JComboBox<>();
        dropdown1.setBounds(140,0,250,50);
        frame.add(dropdown1);

        JLabel fromCurrency = new JLabel("From Currency");
        fromCurrency.setBounds(10,0,100,50);
        frame.add(fromCurrency);

        dropdown2 = new JComboBox<>();
        dropdown2.setBounds(140,50,250,50);
        frame.add(dropdown2);

        JLabel toCurrency = new JLabel("To Currency");
        toCurrency.setBounds(10,50,100,50);
        frame.add(toCurrency);

        JTextField input = new JTextField();
        input.setBounds(10,110,100,30);
        frame.add(input);

        JLabel result = new JLabel();
        result.setBounds(140,110,250,30);
        frame.add(result);

        JButton btn_calc = new JButton("Berechnen");
        btn_calc.setBounds(10,150,100,30);
        frame.add(btn_calc);

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Currency"; // Ihre PostgreSQL-Datenbank-URL
            String user = "postgres"; // Ihr PostgreSQL-Benutzername
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

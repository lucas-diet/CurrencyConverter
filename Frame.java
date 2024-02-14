import java.util.ArrayList;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame {

    private JComboBox<String> dropdown1, dropdown2;

    public void createFrame() throws ClassNotFoundException {
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label1 = new JLabel("Converter");
        label1.setBounds(10,10,110,30);
        frame.add(label1);
        label1.setFont(new Font("Arial", Font.PLAIN, 24));

        dropdown1 = new JComboBox<>();
        dropdown1.setBounds(140,40,250,50);
        frame.add(dropdown1);

        JLabel fromCurrency = new JLabel("From Currency");
        fromCurrency.setBounds(10,40,100,50);
        frame.add(fromCurrency);

        dropdown2 = new JComboBox<>();
        dropdown2.setBounds(140,90,250,50);
        frame.add(dropdown2);

        JLabel toCurrency = new JLabel("To Currency");
        toCurrency.setBounds(10,90,100,50);
        frame.add(toCurrency);

        JTextField input = new JTextField();
        input.setBounds(10,150,100,30);
        frame.add(input);

        JLabel result = new JLabel();
        result.setBounds(140,150,250,30);
        frame.add(result);

        JButton btn_calc = new JButton("Berechnen");
        btn_calc.setBounds(10,190,100,30);
        frame.add(btn_calc);
        btn_calc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    double amount = Integer.valueOf(input.getText());
                    String fromItm = String.valueOf(dropdown1.getSelectedItem());
                    String toItm = String.valueOf(dropdown2.getSelectedItem());
                    
                    System.out.println(amount);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid double input");
                } 
            }
        });

        JLabel label2 = new JLabel("Add Currency");
        label2.setBounds(10,250,150,30);
        frame.add(label2);
        label2.setFont(new Font("Arial", Font.PLAIN, 24));

        JTextField newCurrency = new JTextField();
        newCurrency.setBounds(10,290,100,30);
        frame.add(newCurrency);

        JTextField newShortcut = new JTextField();
        newShortcut.setBounds(150,290,100,30);
        frame.add(newShortcut);

        JButton btn_add = new JButton("Add");
        btn_add.setBounds(10,330,100,30);
        frame.add(btn_add);
        btn_add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("clicked");
            }
        });

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
                fromDataList.add(currency);
                toDataList.add(currency);
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

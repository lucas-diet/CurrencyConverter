import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileInputStream;
import java.io.IOException;

public class Frame {

    private JComboBox<String> dropdown2;

    public String[] getDatabaseData() {
        Properties properties = new Properties();
        try {
            FileInputStream fs = new FileInputStream("config.properties");
            properties.load(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = properties.getProperty("database.url");
        String user = properties.getProperty("database.user");
        String pw = properties.getProperty("database.password");
        
        String[] data = new String[3];
        data[0] = url;
        data[1] = user;
        data[2] = pw;
        
        return data;
    }

    public Connection connectionDB(String url, String user, String pw) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        
        Connection con = DriverManager.getConnection(url, user, pw);
        return con;
    }

    public void createFrame() throws ClassNotFoundException {
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel label1 = new JLabel("Converter");
        label1.setBounds(10,10,110,30);
        frame.add(label1);
        label1.setFont(new Font("Arial", Font.PLAIN, 24));

        JLabel from = new JLabel("Euro");
        from.setBounds(140,40,250,50);
        frame.add(from);

        JLabel fromCurrency = new JLabel("From Currency");
        fromCurrency.setBounds(10,40,100,50);
        frame.add(fromCurrency);

        dropdown2 = new JComboBox<>();
        dropdown2.setBounds(140,90,250,50);
        frame.add(dropdown2);

        JLabel toCurrency = new JLabel("To Currency");
        toCurrency.setBounds(10,90,100,50);
        frame.add(toCurrency);

        JTextField input = new JTextField("Amount");
        input.setBounds(10,150,100,30);
        input.setForeground(Color.GRAY);
        input.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                if (input.getText().equals("Amount")) {
                    input.setText("");
                    input.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent fe) {
                if (input.getText().isEmpty()) {
                    input.setForeground(Color.GRAY);
                    input.setText("Amount");
                }
            }
        });

        frame.add(input);

        JLabel result = new JLabel();
        result.setBounds(140,150,250,30);
        frame.add(result);

        JButton btn_calc = new JButton("Calculate");
        btn_calc.setBounds(10,190,100,30);
        frame.add(btn_calc);
        btn_calc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    double amount = Double.valueOf(input.getText());
                    //String fromItm = String.valueOf(dropdown1.getSelectedItem());
                    String toItm = String.valueOf(dropdown2.getSelectedItem());
                    
                    CurrencyConverter cc = new CurrencyConverter();
                    //String fromCurrency = cc.getCurrency(fromItm);
                    String toCurrency = cc.getCurrency(toItm);

                    //String fromISO = cc.getISO(fromCurrency);
                    String toISO = cc.getISO(toCurrency);

                    double x = cc.convert(amount, toISO);
                    result.setText(x + " " + toISO);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid double input");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        });

        JLabel label2 = new JLabel("Add Currency");
        label2.setBounds(10,250,150,30);
        frame.add(label2);
        label2.setFont(new Font("Arial", Font.PLAIN, 24));
        
        JTextField newCurrency = new JTextField("Currency");
        newCurrency.setBounds(10,290,100,30);
        newCurrency.setForeground(Color.GRAY);

        newCurrency.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                if (newCurrency.getText().equals("Currency")) {
                    newCurrency.setText("");
                    newCurrency.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent fe) {
                if (newCurrency.getText().isEmpty()) {
                    newCurrency.setForeground(Color.GRAY);
                    newCurrency.setText("Currency");
                }
            }
        });

        frame.add(newCurrency);

        JTextField newShortcut = new JTextField("ISO");
        newShortcut.setBounds(150,290,100,30);
        newShortcut.setForeground(Color.GRAY);

        newShortcut.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                if (newShortcut.getText().equals("ISO")) {
                    newShortcut.setText("");
                    newShortcut.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent fe) {
                if (newShortcut.getText().isEmpty()) {
                    newShortcut.setForeground(Color.GRAY);
                    newShortcut.setText("ISO");
                }
            }
        });

        frame.add(newShortcut);

        JTextField newRate = new JTextField("Rate");
        newRate.setBounds(290,290,100,30);
        newRate.setForeground(Color.GRAY);

        newRate.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                if (newRate.getText().equals("Rate")) {
                    newRate.setText("");
                    newRate.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent fe) {
                if (newRate.getText().isEmpty()) {
                    newRate.setForeground(Color.GRAY);
                    newRate.setText("Rate");
                }
            }
        });

        frame.add(newRate);

        JButton btn_add = new JButton("Add");
        btn_add.setBounds(10,330,100,30);
        frame.add(btn_add);
        btn_add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    String currencyInput = newCurrency.getText();
                    String isoInput = newShortcut.getText();
                    double rateInput = Double.valueOf(newRate.getText());
                    
                    CurrencyConverter cc = new CurrencyConverter();

                    cc.updateCurrencys(currencyInput, isoInput);
                    cc.updateExchangeRate(isoInput, rateInput);

                    newCurrency.setText("Currency");
                    newCurrency.setForeground(Color.GRAY);
                    newShortcut.setText("ISO");
                    newShortcut.setForeground(Color.GRAY);
                    newRate.setText("Rate");
                    newRate.setForeground(Color.GRAY);

                    String url = getDatabaseData()[0];
                    String user = getDatabaseData()[1];
                    String pw = getDatabaseData()[2];
                    
                    Connection con = connectionDB(url, user, pw);

                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select currency, shortcut from currencys");

                    ArrayList<String> toDataList = new ArrayList<>();
                    while (rs.next()) {
                        String currency = rs.getString("currency");
                        toDataList.add(currency);
                    }
                    for (String currency : toDataList) {
                        dropdown2.addItem(currency);
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                    
                } catch (NumberFormatException e) {
                    System.out.println("Invalid double input"); 
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            String url = getDatabaseData()[0];
            String user = getDatabaseData()[1];
            String pw = getDatabaseData()[2];
            
            Connection con = connectionDB(url, user, pw);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select currency, shortcut from currencys");

            ArrayList<String> fromDataList = new ArrayList<>();
            ArrayList<String> toDataList = new ArrayList<>();
            while (rs.next()) {
                String currency = rs.getString("currency");
                fromDataList.add(currency);
                toDataList.add(currency);
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
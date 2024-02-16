import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CurrencyConverter {
    
    private Connection con;

    public CurrencyConverter() throws ClassNotFoundException, IOException {
        Properties properties = new Properties();

        try {
            Class.forName("org.postgresql.Driver");
            FileInputStream fs = new FileInputStream("config.properties");
            properties.load(fs);
            fs.close();

            String url = properties.getProperty("database.url");
            String user = properties.getProperty("database.user");
            String pw = properties.getProperty("database.password");
            con = DriverManager.getConnection(url, user, pw);
            createExchangeRateTable();
            createCurrencyTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createExchangeRateTable() throws SQLException {
        String sql = "create table if not exists exchange_rates (shortcut text primary key, rate real)";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public void createCurrencyTable() throws SQLException {
        String sql = "create table if not exists currencys (currency text, shortcut text primary key)";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public void updateExchangeRate(String shortcut, double rate) throws SQLException {
        String sql = "insert into exchange_rates (shortcut, rate) values (?, ?) on conflict (shortcut) do update set rate = excluded.rate";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, shortcut);
            pstmt.setDouble(2, rate);
            pstmt.executeUpdate();
        }
    }

    public void updateCurrencys(String currency, String shortcut) throws SQLException {
        String sql = "insert into currencys (currency, shortcut) values (?, ?) on conflict do nothing";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, currency);
            pstmt.setString(2, shortcut);
            pstmt.executeUpdate();
        }
    }
    
    public double getExchangeRate(String shortcut) throws SQLException {
        String sql = "select rate from exchange_rates where shortcut = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, shortcut);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("rate");
                }
                else {
                    throw new IllegalArgumentException("Invalid currency");
                }
            }
        }
    }

    public double convert(double amount, String toCurrency) throws SQLException {
        double toRate = getExchangeRate(toCurrency);
        return amount * toRate;
    }

    public String getCurrency(String currency) throws SQLException {
        String sql = "select * from currencys as cu, exchange_rates as er where cu.shortcut = er.shortcut and cu.currency = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, currency);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("currency");
                }
                else {
                    throw new IllegalArgumentException("Invalid");
                }
            }
        }
    }

    public String getISO(String currency) throws SQLException {
        String sql = "select * from currencys as cu, exchange_rates as er where cu.shortcut = er.shortcut and cu.currency = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, currency);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("shortcut");
                }
                else {
                    throw new IllegalArgumentException("Invalid");
                }
            }
        }
    }
}
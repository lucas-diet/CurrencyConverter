import java.sql.*;


public class CurrencyConverter {
    
    private Connection con;

    public CurrencyConverter() throws ClassNotFoundException {
        con = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Currency"; // Ihre PostgreSQL-Datenbank-URL
            String user = "lucas"; // Ihr PostgreSQL-Benutzername
            String password = ""; // Ihr PostgreSQL-Passwort
            con = DriverManager.getConnection(url, user, password);
            createExchangeRateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createExchangeRateTable() throws SQLException {
        String sql = "create table if not exists exchange_rates (currency TEXT primary key, rate real)";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public void updateExchangeRate(String currency, double rate) throws SQLException {
        String sql = "insert into exchange_rates (currency, rate) values (?, ?) on conflict (currency) do update set rate = excluded.rate";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, currency);
            pstmt.setDouble(2, rate);
            pstmt.executeUpdate();
        }
    }

    public double getExchangeRate(String currency) throws SQLException {
        String sql = "select rate from exchange_rates where currency = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, currency);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("rate");
                }
                else {
                    throw new IllegalArgumentException("Ungültige Währung");
                }
            }
        }
    }

    public double convert(double amount, String fromCurrency, String toCurrency) throws SQLException {
        double fromRate = getExchangeRate(fromCurrency);
        double toRate = getExchangeRate(toCurrency);
        return amount * (fromRate / toRate);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        CurrencyConverter cc = new CurrencyConverter();

        try {
            cc.updateExchangeRate("EUR", 1.0);
            cc.updateExchangeRate("USD", 1.08);
            cc.updateExchangeRate("AFN", 79.40);
        } catch (SQLException e) {
            e.printStackTrace();;
        }

        try {
            double amount = 1.0;
            String fromCurrency = "EUR";
            String toCurrency = "USD";
            double result = cc.convert(amount, fromCurrency, toCurrency);
            System.out.println(amount + " " + fromCurrency + " entsprechen " + result + " " + toCurrency);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
import java.sql.*;


public class CurrencyConverter {
    

    public static Connection connectionDB() {
        Connection con = null;

        try {
            //Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Currency"; // Ihre PostgreSQL-Datenbank-URL
            String user = "lucas"; // Ihr PostgreSQL-Benutzername
            String password = ""; // Ihr PostgreSQL-Passwort
            con = DriverManager.getConnection(url, user, password);
            // Tabelle für Wechselkurse erstellen, wenn sie nicht existiert
            if (con != null) {
                System.out.println("Verbunden!");
            }
            else {
                System.err.println("Verbindungsfehler!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) throws SQLException {
        connectionDB();
    }

}
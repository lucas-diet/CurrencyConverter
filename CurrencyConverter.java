import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class CurrencyConverter {

    private URL url;

    public CurrencyConverter(URL url) {
        this.url = url;
    }
    
    public void setURL(URL url) {
        this.url = url;
    }

    public URL getURL() {
        return this.url;
    }

    public void connection(URL url) {
        try {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int  responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lesen der Antwort
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                 // Antwort ausgeben
                 System.out.println("Daten von der Webseite:");
                 System.out.println(response.toString());
            } else {
                System.out.println("Fehler beim Abrufen der Daten. Antwortcode: " + responseCode);
            }

            // Verbindung schließen
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MalformedURLException {

        String apiKey = "DEIN_API_SCHLÜSSEL";
        String baseCurrency = "USD"; // Ausgangswährung
        String targetCurrency = "EUR"; // Zielwährung
        
        URL url = new URL("https://v6.exchangeratesapi.io/latest?base=" + baseCurrency + "&symbols=" + targetCurrency + "&access_key=" + apiKey);

        CurrencyConverter cc = new CurrencyConverter(url);

        cc.connection(url);

        System.out.println("Hello!");
    }
}
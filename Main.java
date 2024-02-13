import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        CurrencyConverter cc = new CurrencyConverter();

        try {
            cc.updateCurrencys("Euro", "EUR");
            cc.updateCurrencys("US-Dollar", "USD");
            cc.updateCurrencys("Afghani", "AFN");


            cc.updateExchangeRate("EUR", 1.0);
            cc.updateExchangeRate("USD", 1.08);
            cc.updateExchangeRate("AFN", 79.40);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            double amount = 5;
            String fromCurrency = "EUR";
            String toCurrency = "USD";
            double result = cc.convert(amount, fromCurrency, toCurrency);
            System.out.println(amount + " " + fromCurrency + " entsprechen " + result + " " + toCurrency + "\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

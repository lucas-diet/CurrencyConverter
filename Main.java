import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        
        CurrencyConverter cc = new CurrencyConverter();
        Frame f = new Frame();

        try {
            cc.updateCurrencys("Afghani", "AFN");
            cc.updateCurrencys("Albanischer Lek", "ALL");
            cc.updateCurrencys("Algerischer Dinar", "DZD");
            cc.updateCurrencys("Antillen-Gulden", "ANG");

            cc.updateCurrencys("Euro", "EUR");
            cc.updateCurrencys("US-Dollar", "USD");
            

            cc.updateExchangeRate("ALL", 103.92);
            cc.updateExchangeRate("AFN", 79.40);
            cc.updateExchangeRate("DZD", 144.73);
            cc.updateExchangeRate("ANG", 1.94);

            cc.updateExchangeRate("EUR", 1.0);
            cc.updateExchangeRate("USD", 1.08);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //try {
          //  double amount = 1;
            //String fromCurrency = "USD";
            //String toCurrency = "EUR";
            //double result = cc.convert(amount, toCurrency);
            //System.out.println(amount + " " + fromCurrency + " entsprechen " + result + " " + toCurrency + "\n");
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}


        f.createFrame();


    }
}

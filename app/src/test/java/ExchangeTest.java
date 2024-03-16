import assignment1.app.Currency;
import assignment1.app.Exchange;
import assignment1.app.ExchangeRate;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ExchangeTest {

    @Test
    void testCurrencyReport() {
        Exchange e = new Exchange();
        Currency c1 = Currency.getObject("AUD", e.getCurrencyList());
        Currency c2 = Currency.getObject("USD", e.getCurrencyList());
        LocalDateTime startDate = LocalDateTime.of(2022,8,10,10,0);
        LocalDateTime endDate = LocalDateTime.of(2022,10,10,10,0);
        e.curReport(c1,c2,startDate, endDate);
        assertEquals(e.CR.getMax(),0.68);
    }
    //edit exchange rate tests

    @Test
    void editExchangeRateTest() {
        Exchange ex = new Exchange();
        assertTrue(ex.editExchangeRate("AUD", "USD", 0.5));
    }
    
    // @Test
    // void editExchangeRateTest() {
    //     Exchange ex = new Exchange();
    //     ex.editExchangeRate("AUD", "USD", 0.5);
    //     // assertEquals(ex.getExchangeRate("AUD", "USD"), 0.5);
    //     for (ExchangeRate findRate : ex.getExchangeRateList()){
    //         if((findRate.currency1 == c1) && (findRate.currency2 == c2)){
    //             rateFound = findRate.getRate(c1, c2);
    //         }
    //         else if((findRate.currency1 == c2) && (findRate.currency2 == c1)){
    //             rateFound = findRate.getRate(c1, c2);
    //         }
    //     }
    //     assertEquals(rateFound, 0.5);
    // }

    // @Test
    // void editExchangeRateTest2() {
    //     Exchange ex = new Exchange();
    //     ex.editExchangeRate("AUD", "USD", 0.5);
    //     for (ExchangeRate findRate : ex.getExchangeRateList()){
    //         if((findRate.currency1 == c1) && (findRate.currency2 == c2)){
    //             rateFound = findRate.getRate(c1, c2);
    //         }
    //         else if((findRate.currency1 == c2) && (findRate.currency2 == c1)){
    //             rateFound = findRate.getRate(c1, c2);
    //         }
    //     }
    //     assertNotEquals(rateFound, 0.6);
    // }


    // @Test
    // void editExchangeRateTest5(){
    //     Exchange ex = new Exchange();
    //     ex.editExchangeRate("AUD", "XYZ", 0.5);
    //     assertNull(ex.getExchangeRate("AUD", "XYZ"));
    // }


//    @Test
//    void testCurrencyList() {
//        Exchange exchangeObject = new Exchange();
//        assertTrue(exchangeObject.getCurrencyList().size() > 0);
//    }
//
//    @Test
//    void testExchangeRateList() {
//        Exchange exchangeObject = new Exchange();
//        assertTrue(exchangeObject.getExchangeRateList().size() > 0);
//    }
//
//    @Test
//    void testPopulateCurrencyList() {
//        Exchange exchangeObject = new Exchange();
//        assertTrue(exchangeObject.getPopularCurrencyList().size() > 0);
//    }
//
//    @Test
//    void testFilePath() {
//        Exchange exchangeObject = new Exchange();
//        assertEquals(exchangeObject.getFilePath(), "loadConfig.json");
//    }

      @Test
      void popularPairsEditTest() {
          Exchange exchangeObject = new Exchange();
          Currency c1 = Currency.getObject("AUD", exchangeObject.getCurrencyList());
          Currency c2 = Currency.getObject("USD", exchangeObject.getCurrencyList());
          Currency c3 = Currency.getObject("GBP", exchangeObject.getCurrencyList());
          assertEquals(exchangeObject.editPopularPairs(c1, c2), false);
          assertEquals(exchangeObject.editPopularPairs(c3, c1), true);
      }

      void currencyConvertTest() {
          Exchange exchangeObject = new Exchange();
          Currency c1 = Currency.getObject("AUD", exchangeObject.getCurrencyList());
          Currency c2 = Currency.getObject("USD", exchangeObject.getCurrencyList());
          double amount = 500.00;
          assertEquals(exchangeObject.convertCurrency(c1, c2, amount), 340.0);
      }
}

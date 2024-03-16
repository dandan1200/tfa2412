import assignment1.app.Currency;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyTest {

    @Test
    void testCreateCurrency1() {
        Currency cTest = new Currency("TEST");
        assertEquals(cTest.getTicker(), "TEST");
    }
    @Test
    void testCurrencySearch() {
        Currency c1 = new Currency("1");
        Currency c2 = new Currency("2");
        Currency c3 = new Currency("3");

        List<Currency> currencyList = new ArrayList<Currency>();
        currencyList.add(c1);
        currencyList.add(c2);
        currencyList.add(c2);

        assertEquals(Currency.getObject("2", currencyList), c2);
    }
}

import assignment1.app.Currency;
import assignment1.app.ExchangeRate;
import assignment1.app.LoadData;
import assignment1.app.rateUp;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadDataTest {

    LoadData createLoadData() {
        String filepath = "src/test/resources/testConfig.json";
        return new LoadData(filepath);
    }
    @Test
    void getCurrencies() {
        LoadData ld = createLoadData();
        List<String> trueTickers = new ArrayList<String>();
        trueTickers.add("AUD");
        trueTickers.add("USD");
        trueTickers.add("NZD");
        trueTickers.add("GBP");
        trueTickers.add("EUR");
        trueTickers.add("JPY");
        int i = 0;
        for (Currency c : ld.getCurrencies()) {
            assertEquals(c.getTicker(),trueTickers.get(i));
            i++;
        }
    }
    @Test
    void getPopCurrencies() {
        LoadData ld = createLoadData();
        List<String> trueTickers = new ArrayList<String>();
        trueTickers.add("AUD");
        trueTickers.add("USD");
        trueTickers.add("EUR");
        trueTickers.add("JPY");
        int i = 0;
        for (Currency c : ld.getPopularCurrencies()) {
            assertEquals(c.getTicker(),trueTickers.get(i));
            i++;
        }
    }
    @Test
    void saveNewCurrencyTest() {
        LoadData ld = createLoadData();
        Currency newCur = new Currency("CAD");
        ld.saveNewCurrency(newCur);
        assertTrue(ld.getCurrencies().contains(newCur));
    }
    @Test
    void saveNewExchangeRateTest() {
        LoadData ld = createLoadData();
        Currency c1 = ld.getCurrencies().get(ld.getCurrencies().size()-1);
        Currency c2 = ld.getCurrencies().get(0);
        ExchangeRate newER = new ExchangeRate(c1,c2,1.5, rateUp.UP);
        ld.saveNewExchangeRate(newER);
        assertTrue(ld.getExchangeRates().contains(newER));
    }
    @AfterAll
    static void resetFile() {
        try {
            FileReader fr = new FileReader("src/main/resources/loadConfigBACKUP.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject obj = (JSONObject) jsonParser.parse(fr);
            fr.close();

            FileWriter fw = new FileWriter("src/test/resources/testConfig.json");
            fw.write(obj.toJSONString());
            fw.close();
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

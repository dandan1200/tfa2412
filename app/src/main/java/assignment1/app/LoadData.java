package assignment1.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.plaf.ColorUIResource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadData {
    private String filepath;
    private JSONObject file;
    private List<Currency> currencyList;
    private List<Currency> popularCurrencyList;
    private List<ExchangeRate> exchangeRateList;

    public LoadData(String filepath) {
        this.filepath = filepath;

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filepath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            this.file = (JSONObject) obj;
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        List<Currency> currencyList = new ArrayList<Currency>();
        for (int i = 0; i < ((JSONArray) this.file.get("currencies")).size(); i++) {
            currencyList.add(new Currency((String)((JSONArray) this.file.get("currencies")).get(i)));
        }
        this.currencyList = currencyList;

        List<Currency> popularCurrencyList = new ArrayList<Currency>();
        for (int i = 0; i < ((JSONArray) this.file.get("popularCurrencies")).size(); i++) {
            popularCurrencyList.add(Currency.getObject(((String)((JSONArray) this.file.get("popularCurrencies")).get(i)), this.currencyList));
        }
        this.popularCurrencyList = popularCurrencyList;

        List<ExchangeRate> exchangeRateList = new ArrayList<ExchangeRate>();

        JSONArray jsonExchangeRates = (JSONArray) this.file.get("exchangeRates");

        for (int i = 0; i < jsonExchangeRates.size(); i++) {
            JSONArray rates = (JSONArray) ((JSONObject) jsonExchangeRates.get(i)).get("Rates");
            double currentRate = Double.parseDouble(((String) rates.get(0)).split(",")[1]);
            rateUp isUp = rateUp.NOCHANGE;
            if (rates.size() > 1) {
                if (currentRate > Double.parseDouble(((String) rates.get(1)).split(",")[1])) {
                    isUp = rateUp.UP;
                } else if (currentRate < Double.parseDouble(((String) rates.get(1)).split(",")[1]))  {
                    isUp = rateUp.DOWN;
                }
            }


            exchangeRateList.add(
                    new ExchangeRate(
                        Currency.getObject((String) (((JSONObject) jsonExchangeRates.get(i)).get("cur1")), currencyList),
                        Currency.getObject((String) (((JSONObject) jsonExchangeRates.get(i)).get("cur2")), currencyList),
                        currentRate,
                        isUp
                        )
                    );

        }
        this.exchangeRateList = exchangeRateList;

    }

    public List<Currency> getCurrencies(){
        return this.currencyList;
    }
    public List<Currency> getPopularCurrencies() {
        return this.popularCurrencyList;
    }
    public List<ExchangeRate> getExchangeRates() {
        return this.exchangeRateList;
    }
    private boolean saveConfigToFile(){
        try {
            FileWriter fw = new FileWriter(this.filepath);
            fw.write(this.file.toJSONString());
            fw.close();
            return true;
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean saveNewCurrency(Currency c) {
        ((JSONArray) file.get("currencies")).add(c.getTicker());
        this.currencyList.add(c);
        if (saveConfigToFile()) {
            return true;
        } else {
            return false;
        }
        
    }
    public boolean saveNewExchangeRate(ExchangeRate newExchangeRate) {
        JSONObject newJSONER = new JSONObject();
        newJSONER.put("cur1", newExchangeRate.currency1.getTicker());
        newJSONER.put("cur2", newExchangeRate.currency2.getTicker());
        JSONArray newRateArray = new JSONArray();
        newRateArray.add(java.time.LocalDateTime.now()+","+Double.toString(newExchangeRate.getRate(newExchangeRate.currency1,newExchangeRate.currency2)));
        newJSONER.put("Rates",newRateArray);

        ((JSONArray) file.get("exchangeRates")).add(newJSONER);
        this.exchangeRateList.add(newExchangeRate);
        if (saveConfigToFile()) {
            return true;
        } else {
            return false;
        }

    }
 }

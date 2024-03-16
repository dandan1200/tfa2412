package assignment1.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Collections;

public class CurrencyReport {

    private String filepath;
    private JSONObject file;

    private SortedMap<LocalDateTime, Double> rates = new TreeMap<LocalDateTime, Double>(
            new Comparator<LocalDateTime>() {
                public int compare(LocalDateTime o1, LocalDateTime o2){
                    return o1.compareTo(o2);
                }
            }
            );

    public CurrencyReport(String filepath, Currency cur1, Currency cur2, LocalDateTime startDate, LocalDateTime endDate) {
        this.filepath = filepath;

        //Load File
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filepath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            this.file = (JSONObject) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Load rates
        JSONArray jsonExchangeRates = (JSONArray) this.file.get("exchangeRates");

        for (int i = 0; i < jsonExchangeRates.size(); i++) {
            JSONObject pair = (JSONObject) jsonExchangeRates.get(i);
            if ((pair.get("cur1").equals(cur1.getTicker()) && pair.get("cur2").equals(cur2.getTicker())) || ((pair.get("cur1").equals(cur2.getTicker()) && pair.get("cur2").equals(cur1.getTicker())) )) {
                JSONArray ratesStrings = (JSONArray) pair.get("Rates");

                for (int j = 0; j < ratesStrings.size(); j++) {
                    rates.put(LocalDateTime.parse(ratesStrings.get(j).toString().split(",")[0]),Double.parseDouble(ratesStrings.get(j).toString().split(",")[1] ));
                }
                break;
            }
        }

        rates = rates.subMap(startDate, endDate);

    }

    public String displayRates() {
        String out = "";
        for (int i = 0; i < rates.size(); i++) {
            out += rates.keySet().toArray()[i] + ":" + rates.get(rates.keySet().toArray()[i]);
            System.out.println(rates.keySet().toArray()[i] + ":" + rates.get(rates.keySet().toArray()[i]));
        }
        return out;
    }
    public double getAvg() {
        double avg = 0;
        double sum = 0;
        for (int i = 0; i < rates.size(); i++) {
            sum += (Double) rates.values().toArray()[i];
        }
        avg = (Double) sum/rates.size();
        System.out.println("Average: " + avg);
        return avg;
    }
    public double getMedian() {
        double median = 0;
        if (rates.size() % 2 == 0) {
            median = ((double) (rates.values().toArray()[(rates.size()/2) - 1]) +  (double) (rates.values().toArray()[(rates.size()/2)]))/2;
        } else {
            median = (double) rates.values().toArray()[rates.size()/2];
        }
        System.out.println("Median: " + median);
        return median;
    }
    public double getMax() {
        System.out.println("Max: " + Collections.max(rates.values()));
        return Collections.max(rates.values());
    }
    public double getMin() {
        System.out.println("Min: " + Collections.min(rates.values()));
        return Collections.min(rates.values());
    }
    public double getSD() {
        StandardDeviation sd = new StandardDeviation();
        double vals[] = new double[rates.size()];
        for (int i = 0; i < rates.size(); i++) {
            vals[i] = (double) rates.values().toArray()[i];
        }
        System.out.println("Min: " + sd.evaluate(vals));
        return sd.evaluate(vals);
    }
}

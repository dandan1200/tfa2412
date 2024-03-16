package assignment1.app;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


public class Exchange {
    private final String filePath = "src/main/resources/loadConfig.json";
    public List<Currency> currencyList;
    public List<Currency> popularCurrencyList;
    public List<ExchangeRate> exchangeRateList;

    public CurrencyReport CR;

    private LoadData configData;

    public Exchange(){
        LoadData FxObject = new LoadData(filePath);
        this.configData = FxObject;
        currencyList = FxObject.getCurrencies();
        popularCurrencyList = FxObject.getPopularCurrencies();
        exchangeRateList = FxObject.getExchangeRates();
        
    }

    public String getFilePath() {
        return filePath;
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public List<Currency> getPopularCurrencyList() {
        return popularCurrencyList;
    }

    public List<ExchangeRate> getExchangeRateList() {
        return exchangeRateList;
    }

    public void curReport(Currency c1, Currency c2, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.CR = new CurrencyReport(filePath,c1,c2,startDateTime,endDateTime);
    }

    public void addCurrency(String ticker) {
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();

        Currency newCurrency = new Currency(ticker);
        this.currencyList.add(newCurrency);
        this.configData.saveNewCurrency(newCurrency);

        //loop through currencies and get exchange rates for each one and create exchange rate object
        for (Currency c: this.currencyList) {
            if (c.getTicker() != newCurrency.getTicker()) {
                System.out.println("Enter exchange rate for " + newCurrency.getTicker() + "/" + c.getTicker());
                double rate = 0;
                while (rate == 0) {
                    input = s.nextLine();
                    try{
                        rate = Double.parseDouble(input);
                    } catch (NumberFormatException e) {
                        rate = 0;
                        System.out.println("Invalid rate input.");
                        System.out.println("Enter exchange rate for " + newCurrency.getTicker() + "/" + c.getTicker());
                    }

                }
                ExchangeRate newPair = new ExchangeRate(newCurrency, c, rate, rateUp.NOCHANGE);

                //Save to file
                this.configData.saveNewExchangeRate(newPair);

            }
        }
        s.close();

    }

    public boolean editExchangeRate(String inputc1, String inputc2, double newRate){
        // Takes Currency object and returns current rate and input new rate and save changes to file
        // System.out.println("Enter Base Currency: ");
        // Scanner rateScanner = new Scanner(System.in);
        // String inputc1 = rateScanner.nextLine();
        // System.out.println("Enter Second Currency: ");
        // String inputc2 = rateScanner.nextLine();

        Currency c1 = Currency.getObject(inputc1, currencyList);
        Currency c2 = Currency.getObject(inputc2, currencyList);

        double rateFound = 0;
        Boolean valid = false;

        for (ExchangeRate findRate : exchangeRateList){
            if((findRate.currency1 == c1) && (findRate.currency2 == c2)){
                rateFound = findRate.getRate(c1, c2);
            }
            else if((findRate.currency1 == c2) && (findRate.currency2 == c1)){
                rateFound = findRate.getRate(c1, c2);
            }
            else{
                System.out.println("Exchange Rate not found!");
                
            }
            System.out.println("Current Exchange Rate is: " + rateFound);
            System.out.println("Enter new rate: ");
            // double newRate = rateScanner.nextDouble();

            findRate.updateRate(newRate);
            System.out.println("Stored new exchange rate as " + newRate);
            valid = true;
        }
        return valid;
        

        // rateScanner.close();

    }

    public List<Currency> getPopularPairs(){
        return this.popularCurrencyList;
    }

    public boolean editPopularPairs(Currency currToAdd, Currency currToRemove){
        for (Currency c: popularCurrencyList) {
            System.out.println(c.getTicker());
        }
        if (this.currencyList.contains(currToAdd) && (this.popularCurrencyList.contains(currToAdd) == false)) {
            if (this.popularCurrencyList.contains(currToRemove)) {
                this.popularCurrencyList.remove(currToRemove);
                this.popularCurrencyList.add(currToAdd);
                return true;
            } return false;
        } return false;
    }

    public double convertCurrency(Currency currencyFrom, Currency currencyTo, double amount){

        for (ExchangeRate exchangeRate : exchangeRateList) {
            if (exchangeRate.getRate(currencyFrom, currencyTo) > 0) {
                return exchangeRate.getRate(currencyFrom, currencyTo) * amount;
            }
        }
        return 0;
    }
}

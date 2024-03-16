package assignment1.app;


//import org.json.simple.JSONArray;

//import java.util.List;

import java.time.LocalDateTime;
import java.util.Scanner;

public class app {
    public static void main(String[] args) {

        Exchange exchangeObject = new Exchange();
        Scanner fxScanner = new Scanner(System.in);
        System.out.println("Welcome to the FX Exchange, are you an admin user? (Y/N)");
        String adminInput = fxScanner.nextLine();
        if (adminInput.equals("Y")){
            System.out.println("Available commands: add currency, edit exchange rate, edit popular pairs, currency report, convert currency, display rates, exit");
        }
        else{
            System.out.println("Available commands: convert currency, display rates, currency report, exit");
        }
        System.out.println("Enter command");

        String command = fxScanner.nextLine();
        //fxScanner.close();
        if(command.equals("add currency")){
            if (adminInput.equals("N")){
                System.out.println("You are not an admin user");
            }
            else{

                System.out.println("Add Currency");
                Scanner s = new Scanner(System.in);

                //Get name and create currency
                Boolean validTicker = false;
                System.out.println("Enter the currency name code: ");
                String input = s.nextLine();

                while (validTicker == false) {

                    Currency foundCurrency = null;
                    //Add currency object to list of currencies after merge
                    for (Currency c: exchangeObject.getCurrencyList()) {
                        if (c.getTicker().equals(input)) {
                            System.out.println("That currency already exists, choose a different currency code.");
                            foundCurrency = c;
                            break;
                        }
                    }
                    if (foundCurrency == null) {
                        validTicker = true;
                    } else {
                        System.out.println("Invalid - Enter the currency name code: ");
                        input = s.nextLine();
                    }
                }

                exchangeObject.addCurrency(input);


            }
        }
        
        else if(command.equals("edit exchange rate")){
            if (adminInput.equals("N")){
                System.out.println("You are not an admin user");
            }
            else{
                System.out.println("Enter Base Currency: ");
                String inputc1 = fxScanner.nextLine();
                System.out.println("Enter Second Currency: ");
                String inputc2 = fxScanner.nextLine();
                System.out.println("Enter new exchange rate: ");
                double inputrate = fxScanner.nextDouble();
                exchangeObject.editExchangeRate(inputc1, inputc2, inputrate);
            }
        }
        
        else if(command.equals("edit popular pairs")){
            if (adminInput.equals("N")){
                System.out.println("You are not an admin user");
            }
            else{
                Scanner currScanner = new Scanner(System.in);
                System.out.println("Which currency would you like to make popular");
                String _currToAdd = currScanner.nextLine();
                Currency currToAdd = Currency.getObject(_currToAdd, exchangeObject.currencyList);
                System.out.println("Which popular currency would you like to remove");
                String _currToRemove = currScanner.nextLine();
                Currency currToRemove = Currency.getObject(_currToRemove, exchangeObject.popularCurrencyList);
        
                if (exchangeObject.editPopularPairs(currToAdd, currToRemove) == false) {
                    System.out.println("Bad currency input");
                }
            }
        }

        else if(command.equals("convert currency")){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter currency converting from: ");
            String _currencyFrom = scanner.nextLine();
            System.out.println("Enter currency converting to: ");
            String _currencyTo = scanner.nextLine();
            System.out.println("Enter amount to convert");
            String _amount = scanner.nextLine();

            Currency currencyFrom = Currency.getObject(_currencyFrom, exchangeObject.currencyList);
            Currency currencyTo = Currency.getObject(_currencyTo, exchangeObject.currencyList);

            double amount = Double.valueOf(_amount);

            if (amount > 0) {
                if (exchangeObject.convertCurrency(currencyFrom, currencyTo, amount) == 0) {
                    System.out.println("Invalid currencies entered");
                } else {System.out.println(exchangeObject.convertCurrency(currencyFrom, currencyTo, amount));}
            } else {
                System.out.println("conversion amount must be non-negative");
            }
        
        }


        else if(command.equals("display rates")){
            System.out.println(exchangeObject.exchangeRateList.toString());
        }
        else if(command.equals("currency report")) {

            System.out.println("Currency Report:");
            Boolean validPair = false;
            Scanner rateScanner = new Scanner(System.in);
            CurrencyReport CR = null;

            while (validPair == false) {
                System.out.println("Enter Base Currency: ");
                String inputc1 = rateScanner.nextLine();
                System.out.println("Enter Second Currency: ");
                String inputc2 = rateScanner.nextLine();

                Currency c1 = Currency.getObject(inputc1, exchangeObject.getCurrencyList());
                Currency c2 = Currency.getObject(inputc2, exchangeObject.getCurrencyList());
                try {
                    c1.getTicker();
                    c2.getTicker();
                } catch (Exception e) {
                    System.out.println("Invalid data, re-enter the currencies.");
                    continue;
                }

                System.out.println("Enter start date and time");
                System.out.println("Enter start year:");
                int startYearIn = rateScanner.nextInt();
                System.out.println("Enter start month:");
                int startMonthIn = rateScanner.nextInt();
                System.out.println("Enter start day:");
                int startDayIn = rateScanner.nextInt();
                System.out.println("Enter start hour:");
                int startHourIn = rateScanner.nextInt();
                System.out.println("Enter start minute:");
                int startMinIn = rateScanner.nextInt();

                LocalDateTime startDateTime = LocalDateTime.of(startYearIn, startMonthIn, startDayIn, startHourIn, startMinIn);

                System.out.println("Enter end date and time");
                System.out.println("Enter end year:");
                int endYearIn = rateScanner.nextInt();
                System.out.println("Enter end month:");
                int endMonthIn = rateScanner.nextInt();
                System.out.println("Enter end day:");
                int endDayIn = rateScanner.nextInt();
                System.out.println("Enter end hour:");
                int endHourIn = rateScanner.nextInt();
                System.out.println("Enter end minute:");
                int endMinIn = rateScanner.nextInt();

                LocalDateTime endDateTime = LocalDateTime.of(endYearIn, endMonthIn, endDayIn, endHourIn, endMinIn);

//            System.out.println(startDateTime);
//            System.out.println(endDateTime);
//            System.out.println(startDateTime.compareTo(endDateTime));


                if (c1 != null && c2 != null && startDateTime.compareTo(endDateTime) < 0) {
                    exchangeObject.curReport(c1,c2,startDateTime,endDateTime);
                    validPair = true;

                } else {
                    System.out.println("Invalid data, re-enter the currencies and times.");
                }
            }
            String option = "";
            option = rateScanner.nextLine();
            while (option.equals("Quit") == false) {
                System.out.println("\n- Display rates");
                System.out.println("- Get Average");
                System.out.println("- Get Median");
                System.out.println("- Get Max");
                System.out.println("- Get Min");
                System.out.println("- Get SD");
                System.out.println("Which report would you like: (or type 'Quit')");
                option = rateScanner.nextLine();

                if (option.equals("Display rates")) {
                    exchangeObject.CR.displayRates();
                } else if (option.equals("Get Average")) {
                    exchangeObject.CR.getAvg();
                } else if (option.equals("Get Median")) {
                    exchangeObject.CR.getMedian();
                } else if (option.equals("Get Max")) {
                    exchangeObject.CR.getMax();
                } else if (option.equals("Get Min")) {
                    exchangeObject.CR.getMin();
                } else if (option.equals("Get SD")) {
                    exchangeObject.CR.getSD();
                } else if (option.equals("Quit")) {
                    break;
                } else {
                    System.out.println("Invalid option, enter again.");
                }
            }
        }
        else if(command.equals("exit")){
            System.out.println("Exiting");
        }
        else{
            System.out.println("Invalid command");
        }

        fxScanner.close();
    }


}

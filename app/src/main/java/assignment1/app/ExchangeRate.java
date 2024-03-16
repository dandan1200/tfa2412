package assignment1.app;

public class ExchangeRate {
    Currency currency1;
    Currency currency2;
    double rate;
    rateUp isUp = rateUp.NOCHANGE; //could be an enum as initiating to false doesn't really make sense //Added enum rateUp. (daniel)

    public ExchangeRate(Currency _currency1, Currency _currency2, double _rate, rateUp _isUp) {
        currency1 = _currency1;
        currency2 = _currency2;
        rate = _rate;
        isUp = _isUp;
    }

    public void updateRate(double _newRate) {
        if (_newRate != this.rate) {return;}
        if (_newRate > this.rate) {
            this.rate = _newRate;
            this.isUp = rateUp.UP;
        } else {
            this.rate = _newRate;
            this.isUp = rateUp.DOWN;
        }
    }
 
    public double getRate(Currency _baseCurrency, Currency _otherCurrency) {
        //will be called through a loop of all exchangerate objects in main class
        //currency1 should always be alphabetically before currency2
        //if doesn't return 0.0, then pair has been found
        if (this.currency1 == _baseCurrency) {
            if (this.currency2 == _otherCurrency) {
                return this.rate;
            }
            return 0;
        }
        else if (this.currency2 == _baseCurrency) {
            if (this.currency1 == _otherCurrency) {
                return (1/this.rate);
            }
            return 0;
        }
        return 0;
    }
}

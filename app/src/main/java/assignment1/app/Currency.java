package assignment1.app;

import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public class Currency {

    private String cTicker;

    public Currency(String ticker) {
        cTicker = ticker;
    }

    public String getTicker() {
        return this.cTicker;
    }

    public static Currency getObject(String cname, List<Currency> list) {
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(cname + " " + list.get(i).getTicker());
//            System.out.println(list.get(i).getTicker().equals(cname));
            if (list.get(i).getTicker().equals(cname)) {
                return list.get(i);
            }
        }
        return null;
    }
}
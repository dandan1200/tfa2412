import assignment1.app.Currency;
import assignment1.app.CurrencyReport;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyReportTest {

    CurrencyReport createReport() {
        String filepath = "src/test/resources/testConfig.json";
        Currency c1 = new Currency("AUD");
        Currency c2 = new Currency("USD");
        LocalDateTime startDate = LocalDateTime.of(2022,8,10,10,0);
        LocalDateTime endDate = LocalDateTime.of(2022,10,10,10,0);

        CurrencyReport cr = new CurrencyReport(filepath, c1, c2, startDate, endDate);
        return cr;
    }
    CurrencyReport createReport2() {
        String filepath = "src/test/resources/testConfig.json";
        Currency c1 = new Currency("AUD");
        Currency c2 = new Currency("JPY");
        LocalDateTime startDate = LocalDateTime.of(2022,8,10,10,0);
        LocalDateTime endDate = LocalDateTime.of(2022,10,10,10,0);

        CurrencyReport cr = new CurrencyReport(filepath, c1, c2, startDate, endDate);
        return cr;
    }

    @Test
    void avgReportTest(){
        CurrencyReport cr = createReport();
        assertEquals(cr.getAvg(), 0.66);
    }
    @Test
    void medianReportTest(){
        CurrencyReport cr = createReport();
        assertEquals(cr.getMedian(), 0.66);
    }
    @Test
    void medianReportTest2(){
        CurrencyReport cr = createReport2();
        assertEquals(cr.getMedian(), 95.53);
    }
    @Test
    void maxReportTest(){
        CurrencyReport cr = createReport();
        assertEquals(cr.getMax(), 0.68);
    }

    @Test
    void minReportTest(){
        CurrencyReport cr = createReport();
        assertEquals(cr.getMin(), 0.64);
    }
    @Test
    void SDReportTest(){
        CurrencyReport cr = createReport();
        assertEquals(cr.getSD(), 0.028284271247461926);
    }
    @Test
    void displayReportTest(){
        CurrencyReport cr = createReport2();
        assertEquals(cr.displayRates(), "2022-09-03T07:43:38.370364:95.53" );
    }
}

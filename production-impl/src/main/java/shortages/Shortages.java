package shortages;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Shortages {
    private final String productRefNo;
    private final LocalDate date;
    private final Map<LocalDate, Long> shortages;

    public Shortages(String productRefNo, LocalDate date, Map<LocalDate, Long> shortages) {
        this.productRefNo = productRefNo;
        this.date = date;
        this.shortages = shortages;
    }

    public static Builder builder(String productRefNo, LocalDate date) {
        return new Builder(productRefNo, date);
    }

    public static class Builder {
        private final String productRefNo;
        private final LocalDate date;
        private final Map<LocalDate, Long> shortages = new HashMap<>();

        public Builder(String productRefNo, LocalDate date) {
            this.productRefNo = productRefNo;
            this.date = date;
        }

        public void forDay(LocalDate day, long levelOnDelivery) {
            shortages.put(day, -levelOnDelivery);
        }

        public Shortages build() {
            return new Shortages(productRefNo, date, shortages);
        }
    }
}

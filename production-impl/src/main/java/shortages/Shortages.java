package shortages;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

class Shortages {

    private final String productRefNo;
    private final LocalDate date;
    private final Map<LocalDate, Long> shortages;

    Shortages(String productRefNo, LocalDate date, Map<LocalDate, Long> shortages) {
        this.productRefNo = productRefNo;
        this.date = date;
        this.shortages = shortages;
    }

    static Builder builder(String productRefNo, LocalDate date) {
        return new Builder(productRefNo, date);
    }

    String getProductRefNo() {
        return productRefNo;
    }

    LocalDate getDate() {
        return date;
    }

    <T> Stream<T> mapForEach(BiFunction<LocalDate, Long, T> func) {
        return shortages.entrySet().stream()
                .map(e -> func.apply(e.getKey(), e.getValue()));
    }

    static class Builder {
        private final String productRefNo;
        private final LocalDate date;
        private final Map<LocalDate, Long> shortages = new HashMap<>();

        Builder(String productRefNo, LocalDate date) {
            this.productRefNo = productRefNo;
            this.date = date;
        }

        void forDay(LocalDate day, long levelOnDelivery) {
            shortages.put(day, -levelOnDelivery);
        }

        Shortages build() {
            return new Shortages(productRefNo, date, shortages);
        }
    }
}

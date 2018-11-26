package shortages;

import java.time.LocalDate;
import java.util.Map;

public class Demands {

    private final Map<LocalDate, Demand> demandsPerDay;

    public Demands(Map<LocalDate, Demand> demands) {
        demandsPerDay = demands;
    }

    public Demand get(LocalDate day) {
        return demandsPerDay.getOrDefault(day, null);
    }

    public static class Demand {
        private final long level;
        private final LevelOnDeliveryCalculation calculation;

        public Demand(long level, LevelOnDeliveryCalculation calculationVariant) {
            this.level = level;
            this.calculation = calculationVariant;
        }

        public long getLevel() {
            return level;
        }

        public long calculate(long stockLevel, long produced) {
            return calculation.calculate(stockLevel, this, produced);
        }
    }
}

package shortages;

import enums.DeliverySchema;

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
        private final DeliverySchema deliverySchema;
        private final long level;

        public Demand(DeliverySchema deliverySchema, long level) {
            this.deliverySchema = deliverySchema;
            this.level = level;
        }

        public DeliverySchema getDeliverySchema() {
            return deliverySchema;
        }

        public long getLevel() {
            return level;
        }
    }
}

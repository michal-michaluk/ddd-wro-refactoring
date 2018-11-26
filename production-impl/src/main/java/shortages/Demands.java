package shortages;

import entities.DemandEntity;
import enums.DeliverySchema;
import tools.Util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demands {

    private final Map<LocalDate, DemandEntity> demandsPerDay;

    public Demands(List<DemandEntity> demands) {
        demandsPerDay = new HashMap<>();
        for (DemandEntity demand1 : demands) {
            demandsPerDay.put(demand1.getDay(), demand1);
        }
    }

    public Demand get(LocalDate day) {
        DemandEntity demand = demandsPerDay.get(day);
        if (demand != null) {
            return new Demand(Util.getDeliverySchema(demand), Util.getLevel(demand));
        } else {
            return null;
        }
    }

    public static class Demand {
        private DeliverySchema deliverySchema;
        private long level;

        private Demand(DeliverySchema deliverySchema, long level) {
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

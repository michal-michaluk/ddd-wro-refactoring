package shortages;

import enums.DeliverySchema;
import external.CurrentStock;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.util.List;

class ShortageSomething {
    private CurrentStock stock;
    private List<LocalDate> dates;
    private Production outputs;
    private Demands demandsPerDay;

    public ShortageSomething(CurrentStock stock, List<LocalDate> dates, Production outputs, Demands demandsPerDay) {
        this.stock = stock;
        this.dates = dates;
        this.outputs = outputs;
        this.demandsPerDay = demandsPerDay;
    }

    public Shortages findShortages() {
        long level = stock.getLevel();
        Shortages.Builder shortage = Shortages.builder(outputs.getProductRefNo(), LocalDate.now());
        for (LocalDate day : dates) {
            Demands.Demand demand = demandsPerDay.get(day);

            long produced = outputs.get(day);
            long levelOnDelivery = demand.getLevelOnDelivery(level, produced);

            if (levelOnDelivery < 0) {
                shortage.forDay(day, levelOnDelivery);
            }
            long endOfDayLevel = level + produced - demand.getLevel();
            level = endOfDayLevel >= 0 ? endOfDayLevel : 0;
        }
        return shortage.build();
    }


    private long getLevelOnDelivery(long level, Demands.Demand demand, long produced) {
        long levelOnDelivery;
        if (demand.getDeliverySchema() == DeliverySchema.atDayStart) {
            levelOnDelivery = level - demand.getLevel();
        } else if (demand.getDeliverySchema() == DeliverySchema.tillEndOfDay) {
            levelOnDelivery = level - demand.getLevel() + produced;
        } else if (demand.getDeliverySchema() == DeliverySchema.every3hours) {
            // TODO WTF ?? we need to rewrite that app :/
            throw new NotImplementedException();
        } else {
            // TODO implement other variants
            throw new NotImplementedException();
        }
        return levelOnDelivery;
    }

}

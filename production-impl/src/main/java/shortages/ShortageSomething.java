package shortages;

import entities.ShortageEntity;
import enums.DeliverySchema;
import external.CurrentStock;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.util.LinkedList;
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

    public List<ShortageEntity> findShortages() {
        long level = stock.getLevel();

        List<ShortageEntity> gap = new LinkedList<>();
        for (LocalDate day : dates) {
            Demands.Demand demand = demandsPerDay.get(day);
            if (demand == null) {
                // introduce Null Object Pattern / Guardian
                level += outputs.get(day);
                continue;
            }
            long produced = outputs.get(day);

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

            if (!(levelOnDelivery >= 0)) {
                ShortageEntity entity = new ShortageEntity();
                entity.setRefNo(outputs.getProductRefNo());
                entity.setFound(LocalDate.now());
                entity.setMissing(levelOnDelivery * -1L);
                entity.setAtDay(day);
                gap.add(entity);
            }
            long endOfDayLevel = level + produced - demand.getLevel();
            level = endOfDayLevel >= 0 ? endOfDayLevel : 0;
        }
        return gap;
    }
}

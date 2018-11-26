package shortages;

import entities.DemandEntity;
import entities.ProductionEntity;
import entities.ShortageEntity;
import external.CurrentStock;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinder {

    /**
     * Production at day of expected delivery is quite complex:
     * We are able to produce and deliver just in time at same day
     * but depending on delivery time or scheme of multiple deliveries,
     * we need to plan properly to have right amount of parts ready before delivery time.
     * <p/>
     * Typical schemas are:
     * <li>Delivery at prod day start</li>
     * <li>Delivery till prod day end</li>
     * <li>Delivery during specified shift</li>
     * <li>Multiple deliveries at specified times</li>
     * Schema changes the way how we calculate shortages.
     * Pick of schema depends on customer demand on daily basis and for each product differently.
     * Some customers includes that information in callof document,
     * other stick to single schema per product. By manual adjustments of demand,
     * customer always specifies desired delivery schema
     * (increase amount in scheduled transport or organize extra transport at given time)
     * <p>
     * TODO algorithm is finding wrong shortages, when more productions is planned in a single day
     */
    public static List<ShortageEntity> findShortages(LocalDate today, int daysAhead, CurrentStock stock,
                                                     List<ProductionEntity> productions, List<DemandEntity> demands) {
        ShortageSomethingRepository repository = new ShortageSomethingRepository(stock, today, daysAhead, new ProductionRepository(productions), new DemandRepository(demands));
        ShortageSomething shortageSomething = repository.get();
        List<ShortageEntity> gap = shortageSomething.findShortages();
        return gap;
    }

    private ShortageFinder() {
    }

}

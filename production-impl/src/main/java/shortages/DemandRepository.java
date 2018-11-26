package shortages;

import entities.DemandEntity;
import enums.DeliverySchema;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tools.Util;

import java.util.List;

import static java.util.stream.Collectors.toMap;

class DemandRepository {
    private List<DemandEntity> demands;

    public DemandRepository(List<DemandEntity> demands) {
        this.demands = demands;
    }

    public Demands get() {
        // demands = dao.fetchDemands()

        return new Demands(demands.stream()
                .collect(toMap(
                        DemandEntity::getDay,
                        demand -> new Demands.Demand(
                                Util.getLevel(demand),
                                mapToCalculation(Util.getDeliverySchema(demand))
                        )
                )));
    }

    private LevelOnDeliveryCalculation mapToCalculation(DeliverySchema deliverySchema) {
        if (deliverySchema == DeliverySchema.atDayStart) {
            return LevelOnDeliveryCalculation.AtDayStart;
        } else if (deliverySchema == DeliverySchema.tillEndOfDay) {
            return LevelOnDeliveryCalculation.TillEndOfDay;
        } else {
            // TODO implement other variants
            return (level, demand, produced) -> {
                throw new NotImplementedException();
            };
        }
    }
}

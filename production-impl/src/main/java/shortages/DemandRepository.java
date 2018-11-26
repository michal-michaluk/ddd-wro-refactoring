package shortages;

import entities.DemandEntity;
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
                                Util.getDeliverySchema(demand),
                                Util.getLevel(demand)
                        )
                )));
    }
}

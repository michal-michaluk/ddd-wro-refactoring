package shortages;

import entities.DemandEntity;
import entities.ProductionEntity;
import entities.ShortageEntity;
import external.CurrentStock;
import tools.Reconciliation;
import tools.ShortageFinder;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ShortageFinderACL {

    public static List<ShortageEntity> findShortages(LocalDate today, int daysAhead, CurrentStock stock,
                                                     List<ProductionEntity> productions, List<DemandEntity> demands) {
        return new Reconciliation<List<ShortageEntity>>(
                Features.ENABLE_NEW_SHORTAGES_CALCULATION.isActive(),
                Features.RETURN_NEW_SHORTAGES_CALCULATION.isActive())
                .invoke(
                        () -> new ShortageFinderScenario(today, daysAhead, stock, productions, demands),
                        () -> ShortageFinder.findShortages(today, daysAhead, stock, productions, demands),
                        () -> {
                            ShortageSomethingRepository repository = new ShortageSomethingRepository(stock, today, daysAhead, new ProductionRepository(productions), new DemandRepository(demands));
                            ShortageSomething shortageSomething = repository.get();
                            return toEntities(shortageSomething.findShortages());
                        },
                        ShortageFinderACL::compareResults
                );
    }

    private static List<ShortageEntity> toEntities(Shortages shortages) {
        return shortages.mapForEach((date, missing) -> {
            ShortageEntity entity = new ShortageEntity();
            entity.setRefNo(shortages.getProductRefNo());
            entity.setFound(shortages.getDate());
            entity.setMissing(missing);
            entity.setAtDay(date);
            return entity;
        }).collect(toList());
    }

    private static Reconciliation.Diff<List<ShortageEntity>> compareResults(List<ShortageEntity> oldModelCalculation, List<ShortageEntity> newModelCalculation) {
        return new Reconciliation.Diff<>();
    }

    private static class ShortageFinderScenario implements Reconciliation.Scenario {

        public ShortageFinderScenario(LocalDate today, int daysAhead, CurrentStock stock, List<ProductionEntity> productions, List<DemandEntity> demands) {
        }

        @Override
        public void print(PrintStream out) {

        }
    }

    private ShortageFinderACL() {
    }
}

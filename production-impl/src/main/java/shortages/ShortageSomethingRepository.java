package shortages;

import external.CurrentStock;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class ShortageSomethingRepository {
    private CurrentStock stock;
    private final LocalDate today;
    //private final Clock clock;

    private ProductionRepository productionRepository;
    private DemandRepository demandRepository;
    private final int daysAhead;


    public ShortageSomethingRepository(CurrentStock stock, LocalDate today, int daysAhead, ProductionRepository productionRepository, DemandRepository demandRepository) {
        this.stock = stock;
        this.today = today;
        this.daysAhead = daysAhead;
        this.productionRepository = productionRepository;
        this.demandRepository = demandRepository;
    }

    public ShortageSomething get() {
        //today = LocalDate.now(clock);
        List<LocalDate> dates = Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysAhead)
                .collect(toList());
        Production outputs = productionRepository.get();
        Demands demandsPerDay = demandRepository.get();
        return new ShortageSomething(stock, dates, outputs, demandsPerDay);
    }
}

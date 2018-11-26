package shortages;

import external.CurrentStock;

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
        Shortages.Builder shortage = Shortages.builder(
                outputs.getProductRefNo(),
                LocalDate.now()
        );
        for (LocalDate day : dates) {
            Demands.Demand demand = demandsPerDay.get(day);
            long produced = outputs.get(day);

            long levelOnDelivery = demand.calculate(level, produced);

            if (levelOnDelivery < 0) {
                shortage.forDay(day, levelOnDelivery);
            }
            long endOfDayLevel = level + produced - demand.getLevel();
            level = endOfDayLevel >= 0 ? endOfDayLevel : 0;
        }
        return shortage.build();
    }

}

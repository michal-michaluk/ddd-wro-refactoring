package shortages;

interface LevelOnDeliveryCalculation {
    LevelOnDeliveryCalculation AtDayStart = (level, demand, produced) -> level - demand.getLevel();
    LevelOnDeliveryCalculation TillEndOfDay = (level, demand, produced) -> level - demand.getLevel() + produced;

    long calculate(long level, Demands.Demand demand, long produced);
}

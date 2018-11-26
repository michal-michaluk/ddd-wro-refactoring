package shortages;

import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

class ProductionRepository {
    private List<ProductionEntity> productions;

    public ProductionRepository(List<ProductionEntity> productions) {
        this.productions = productions;
    }

    public Production get() {
        // productions = dao.fetchProduction()

        String productRefNo = productions
                .stream()
                .map(e -> e.getForm().getRefNo()).findAny()
                .orElse(null);
        Map<LocalDate, Long> map = productions.stream().collect(
                toMap(
                        production -> production.getStart().toLocalDate(),
                        ProductionEntity::getOutput
                )
        );
        return new Production(productRefNo, map);
    }
}

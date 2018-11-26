package shortages;

import java.time.LocalDate;
import java.util.Map;

public class Production {

    private final Map<LocalDate, Long> outputs;
    private final String productRefNo;

    public Production(String productRefNo, Map<LocalDate, Long> productions) {
        this.productRefNo = productRefNo;
        outputs = productions;
    }

    public long get(LocalDate day) {
        return outputs.getOrDefault(day, 0L);
    }

    public String getProductRefNo() {
        return productRefNo;
    }
}

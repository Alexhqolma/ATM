package bank.atm.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BillResponseDto {
    private Long id;
    private BigDecimal count;
}

package bank.atm.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountResponseDto {
    private Long id;
    private Long accountNumber;
    private Long userId;
    private BigDecimal money;
}

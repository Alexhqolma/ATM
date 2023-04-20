package bank.atm.dto.request;

import java.math.BigDecimal;

public class AccountRequestDto {
    private Long accountNumber;
    private Long userId;
    private BigDecimal money;

    public BigDecimal getMoney() {
        return money;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
}

package bank.atm.dto.request;

public class AccountRequestDto {
    private Long accountNumber;
    private Long userId;
    private Long money;

    public Long getMoney() {
        return money;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
}

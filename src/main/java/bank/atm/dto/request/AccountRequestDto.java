package bank.atm.dto.request;

public class AccountRequestDto {
    private Long accountNumber;
    private Long userId;
    private double money;

    public double getMoney() {
        return money;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
}

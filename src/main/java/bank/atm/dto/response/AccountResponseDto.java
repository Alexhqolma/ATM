package bank.atm.dto.response;

import lombok.Data;

@Data
public class AccountResponseDto {
    private Long id;
    private Long accountNumber;
    private Long userId;
    private double money;
}

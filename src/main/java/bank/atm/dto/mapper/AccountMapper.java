package bank.atm.dto.mapper;

import bank.atm.dto.request.AccountRequestDto;
import bank.atm.dto.response.AccountResponseDto;
import bank.atm.model.Account;
import bank.atm.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    private final UserService userService;

    public AccountMapper(UserService userService) {
        this.userService = userService;
    }

    public Account toModel(AccountRequestDto accountRequestDto) {
        Account account = new Account();
        account.setAccountNumber(account.getAccountNumber());
        account.setUser(userService.findById(accountRequestDto.getUserId()));
        account.setMoney(accountRequestDto.getMoney());
        return account;
    }

    public AccountResponseDto toDto(Account account) {
        AccountResponseDto dto = new AccountResponseDto();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setUserId(account.getUser().getId());
        dto.setMoney(account.getMoney());
        return dto;
    }
}

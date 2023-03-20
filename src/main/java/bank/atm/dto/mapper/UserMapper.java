package bank.atm.dto.mapper;

import bank.atm.dto.request.UserRequestDto;
import bank.atm.dto.response.UserResponseDto;
import bank.atm.model.Account;
import bank.atm.model.User;
import bank.atm.service.AccountService;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final AccountService accountService;

    public UserMapper(AccountService accountService) {
        this.accountService = accountService;
    }

    public User toModel(UserRequestDto userRequestDto) {
        User user = new User();
        user.setLogin(userRequestDto.getLogin());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(userRequestDto.getRole());
        user.setAccounts(userRequestDto.getAccountsId().stream()
                .map(accountService::findById)
                .collect(Collectors.toList()));
        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setAccountsId(user.getAccounts().stream()
                .map(Account::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}

package bank.atm.controller;

import bank.atm.dto.mapper.AccountMapper;
import bank.atm.dto.mapper.BillMapper;
import bank.atm.dto.request.AccountRequestDto;
import bank.atm.dto.response.AccountResponseDto;
import bank.atm.model.Account;
import bank.atm.model.User;
import bank.atm.service.AccountService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final BillMapper billMapper;

    public AccountController(AccountService accountService,
                             AccountMapper accountMapper,
                             BillMapper billMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.billMapper = billMapper;
    }

    @PostMapping("/add")
    public AccountResponseDto createAccount(@RequestBody AccountRequestDto accountRequestDto,
                                            @RequestBody User user) {
        Account account = accountMapper.toModel(accountRequestDto);
        account.setUser(user);
        accountService.save(account);
        return accountMapper.toDto(account);
    }

    @GetMapping
    public List<AccountResponseDto> getAllAccounts() {
        return accountService.findAll()
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public List<AccountResponseDto> getAllAccountsByUserId(@PathVariable Long id) {
        return accountService.findAccountsByUserId(id)
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountResponseDto getAccountById(@PathVariable Long id) {
        return accountMapper.toDto(accountService.findById(id));
    }
}

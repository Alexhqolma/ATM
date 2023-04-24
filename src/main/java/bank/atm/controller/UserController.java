package bank.atm.controller;

import bank.atm.dto.mapper.BillMapper;
import bank.atm.dto.mapper.UserMapper;
import bank.atm.dto.request.BillRequestDto;
import bank.atm.dto.response.UserResponseDto;
import bank.atm.model.Account;
import bank.atm.model.Bill;
import bank.atm.service.AccountService;
import bank.atm.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final BillMapper billMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AccountService accountService;

    public UserController(BillMapper billMapper,
                          UserService userService,
                          UserMapper userMapper,
                          AccountService accountService) {
        this.billMapper = billMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.accountService = accountService;
    }

    @PostMapping("/top-up-account/{accountId}")
    public UserResponseDto topUpAccount(@RequestBody BillRequestDto billRequestDto,
                                        @PathVariable Long accountId) {
        Bill bill = billMapper.toModel(billRequestDto);
        accountService.addBillToAccount(bill, accountId);
        Long userId = accountService.findById(accountId).getUser().getId();
        return userMapper.toDto(userService.findById(userId));
    }

    @PostMapping
    public UserResponseDto sendBillToAccount(
            @RequestParam(value = "accountFromId") Long accountFromId,
            @RequestParam(value = "accountToId") Long accountToId,
            @RequestParam(value = "sum", defaultValue = "0") int sum) {
        userService.sendFromAccountToAccount(accountFromId, accountToId, sum);
        Account account = accountService.findById(accountFromId);
        return userMapper.toDto(userService.findById(account.getUser().getId()));
    }

    @PostMapping("/get-money")
    public UserResponseDto getMoneyFromAccount(
            @RequestParam(value = "accountId") Long accountId,
            @RequestParam(value = "sum", defaultValue = "0") int sum) {
        userService.getMoneyFromAccount(accountId, sum);
        Account account = accountService.findById(accountId);
        return userMapper.toDto(userService.findById(account.getUser().getId()));
    }
}

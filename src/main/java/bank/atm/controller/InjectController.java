package bank.atm.controller;

import bank.atm.model.Account;
import bank.atm.model.Bill;
import bank.atm.model.User;
import bank.atm.service.AccountService;
import bank.atm.service.BillService;
import bank.atm.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InjectController {
    private final UserService userService;
    private final AccountService accountService;
    private final BillService billService;

    public InjectController(UserService userService,
                            AccountService accountService,
                            BillService billService) {
        this.userService = userService;
        this.accountService = accountService;
        this.billService = billService;
    }

    @GetMapping("/inject")
    public String injectUser() {
        for (int i = 0; i < 5; i++) {
            billService.save(new Bill(BigDecimal.valueOf(100)));
        }

        User user = new User();
        user.setLogin("a");
        user.setPassword("a");
        user.setRole("ADMIN");
        userService.save(user);
        User user2 = new User();
        user2.setLogin("b");
        user2.setPassword("b");
        user2.setRole("USER");
        userService.save(user2);

        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber(456789745L);
        account.setMoney(BigDecimal.valueOf(1000));
        accountService.save(account);
        Account account2 = new Account();
        account2.setUser(user2);
        account2.setAccountNumber(778875556464L);
        accountService.save(account2);

        accountService.addBillToAccount(new Bill(BigDecimal.valueOf(200)), account.getId());
        accountService.addBillToAccount(new Bill(BigDecimal.valueOf(100)), account.getId());
        accountService.addBillToAccount(new Bill(BigDecimal.valueOf(200)), account.getId());
        accountService.addBillToAccount(new Bill(BigDecimal.valueOf(500)), account.getId());
        accountService.addBillToAccount(new Bill(BigDecimal.valueOf(500)), account.getId());

        userService.sendFromAccountToAccount(account.getId(), account2.getId(), 400);

        userService.getMoneyFromAccount(account.getId(), 900);

        List<Bill> bills = List.of(new Bill(BigDecimal.valueOf(100)),
                new Bill(BigDecimal.valueOf(200)),
                new Bill(BigDecimal.valueOf(500)),
                new Bill(BigDecimal.valueOf(500)),
                new Bill(BigDecimal.valueOf(100)));
        userService.addBillToAtm(user, bills);

        return "Injection Done!";

    }
}

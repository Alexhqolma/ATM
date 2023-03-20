package bank.atm.controller;

import bank.atm.model.Account;
import bank.atm.model.Bill;
import bank.atm.model.Storage;
import bank.atm.model.User;
import bank.atm.service.AccountService;
import bank.atm.service.BillService;
import bank.atm.service.StorageService;
import bank.atm.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InjectController {
    private final UserService userService;
    private final AccountService accountService;
    private final BillService billService;
    private final StorageService storageService;

    public InjectController(UserService userService,
                            AccountService accountService,
                            BillService billService,
                            StorageService storageService) {
        this.userService = userService;
        this.accountService = accountService;
        this.billService = billService;
        this.storageService = storageService;
    }

    @GetMapping("/inject")
    public String injectUser() {
        /** Add Bills */
        Bill bill100 = new Bill();
        bill100.setCount(100);
        Bill bill1002 = new Bill();
        bill1002.setCount(100);
        Bill bill200 = new Bill();
        bill200.setCount(200);
        Bill bill500 = new Bill();
        bill500.setCount(500);
        Bill bill5002 = new Bill();
        bill5002.setCount(500);
        billService.save(bill100);
        billService.save(bill200);
        billService.save(bill500);
        billService.save(bill1002);
        billService.save(bill5002);

        /** Add Users */
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

        /** Add Accounts */
        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber(456789745L);
        accountService.save(account);
        Account account2 = new Account();
        account2.setUser(user2);
        account2.setAccountNumber(778875556464L);
        accountService.save(account2);

        /** Add Storage (will be only one for now with ID = 1) */
        Storage storage = new Storage();
        storageService.save(storage);

        /** Add Bills to Account */
        accountService.addBillToAccount(bill100, account.getId());
        accountService.addBillToAccount(bill1002, account.getId());
        accountService.addBillToAccount(bill200, account.getId());
        accountService.addBillToAccount(bill500, account.getId());
        accountService.addBillToAccount(bill5002, account.getId());

        /** Send Money between accounts */

        userService.sendFromAccountToAccount(account.getId(), account2.getId(), 400);

        /** Get money from account */

        userService.getMoneyFromAccount(account.getId(), 500);

        return "Injection Done!";

    }
}

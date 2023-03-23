package bank.atm.service.impl;

import bank.atm.model.Account;
import bank.atm.model.Bill;
import bank.atm.model.User;
import bank.atm.repository.UserRepository;
import bank.atm.service.AccountService;
import bank.atm.service.BillService;
import bank.atm.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final int BILL_CRITERIA = 100;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final BillService billService;
    private static final int BILLFIVE = 500;
    private static final int BILLTWO = 200;
    private static final int BILLONE = 100;

    public UserServiceImpl(UserRepository userRepository,
                           AccountService accountService,
                           BillService billService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.billService = billService;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find user by id: " + id));
    }

    @Override
    public User topUpAccount(Bill bill, Long accountId, Long userId) {
        Account account = accountService.findById(accountId);
        account.setMoney(account.getMoney() + bill.getCount());
        accountService.save(account);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Can't find user by id: " + userId));
    }

    @Transactional
    @Override
    public User sendFromAccountToAccount(Long accountFromId, Long accountToId, int sum) {
        Account accountFrom = accountService.findById(accountFromId);
        checkBill(sum);
        checkAccount(accountFrom, sum);
        accountFrom.setMoney(accountFrom.getMoney() - sum);
        Account accountTo = accountService.findById(accountToId);
        accountTo.setMoney(accountTo.getMoney() + sum);
        accountService.save(accountFrom);
        accountService.save(accountTo);
        return userRepository.findById(accountFrom.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Can't find user by id "
                        + accountFrom.getUser().getId()));
    }

    @Transactional
    @Override
    public User getMoneyFromAccount(Long accountId, int sum) {
        Account account = accountService.findById(accountId);
        checkBill(sum);
        checkAccount(account, sum);
        User user = userRepository.findById(account.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Can't find user by id "
                        + account.getUser().getId()));
        account.setMoney(account.getMoney() - sum);
        deleteBillsFromAtm(sum);
        accountService.save(account);
        return user;
    }

    @Override
    public User addBillToAtm(User user, List<Bill> bills) {
        for (Bill bill : bills) {
            billService.save(bill);
        }
        return user;
    }

    private void checkBill(int sum) {
        if (sum % BILL_CRITERIA != 0 || sum <= 0) {
            throw new RuntimeException("Incorrect sum");
        }
    }

    private void checkAccount(Account account, int sum) {
        if (account.getMoney() < sum) {
            throw new RuntimeException("You have not enough money on your account!");
        }
    }

    private void deleteBillsFromAtm(int sum) {
        while (sum - BILLFIVE >= 0) {
            billService.delete(billService.findByCount(BILLFIVE).get(0));
            sum = sum - BILLFIVE;
        }
        while (sum - BILLTWO >= 0) {
            billService.delete(billService.findByCount(BILLTWO).get(0));
            sum = sum - BILLTWO;
        }
        while (sum - BILLONE >= 0) {
            billService.delete(billService.findByCount(BILLONE).get(0));
            sum = sum - BILLONE;
        }
    }
}

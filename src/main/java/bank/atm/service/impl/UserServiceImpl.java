package bank.atm.service.impl;

import bank.atm.model.Account;
import bank.atm.model.Bill;
import bank.atm.model.Storage;
import bank.atm.model.User;
import bank.atm.repository.UserRepository;
import bank.atm.service.AccountService;
import bank.atm.service.StorageService;
import bank.atm.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final int BILL_CRITERIA = 100;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final StorageService storageService;

    public UserServiceImpl(UserRepository userRepository,
                           AccountService accountService,
                           StorageService storageService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.storageService = storageService;
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
        Storage storage = storageService.findById(1L);
        List<Bill> storageBills = storage.getBills();
        storageBills.add(bill);
        storage.setBills(storageBills);
        storageService.save(storage);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Can't find user by id: " + userId));
    }

    @Override
    public User sendFromAccountToAccount(Long accountFromId, Long accountToId, int sum) {
        Account accountFrom = accountService.findById(accountFromId);
        billChecker(sum);
        accountChecker(accountFrom, sum);
        accountFrom.setMoney(accountFrom.getMoney() - sum);
        Account accountTo = accountService.findById(accountToId);
        accountTo.setMoney(accountTo.getMoney() + sum);
        accountService.save(accountFrom);
        accountService.save(accountTo);
        return userRepository.findById(accountFrom.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Can't find user by id "
                        + accountFrom.getUser().getId()));
    }

    @Override
    public User getMoneyFromAccount(Long accountId, int sum) {
        Account account = accountService.findById(accountId);
        billChecker(sum);
        accountChecker(account, sum);
        storageChecker(sum);
        User user = userRepository.findById(account.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Can't find user by id "
                        + account.getUser().getId()));
        account.setMoney(account.getMoney() - sum);
        accountService.save(account);
        return user;
    }

    private void billChecker(int sum) {
        if (sum % BILL_CRITERIA != 0 || sum <= 0) {
            throw new RuntimeException("Incorrect sum");
        }
    }

    private void accountChecker(Account account, int sum) {
        if (account.getMoney() < sum) {
            throw new RuntimeException("You have not enough money on your account!");
        }
    }

    private void storageChecker(int sum) {
        Storage storage = storageService.findById(1L);
        List<Bill> bills = storage.getBills();
        int storageSum = 0;
        for (int i = 0; i < bills.size(); i++) {
            storageSum += bills.get(i).getCount() + storageSum;
        }
        if (sum > storageSum) {
            throw new RuntimeException("Not enough money in ATM");
        }
    }
}

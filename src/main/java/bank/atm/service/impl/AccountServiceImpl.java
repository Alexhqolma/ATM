package bank.atm.service.impl;

import bank.atm.model.Account;
import bank.atm.model.Bill;
import bank.atm.repository.AccountRepository;
import bank.atm.service.AccountService;
import bank.atm.service.BillService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BillService billService;

    public AccountServiceImpl(AccountRepository accountRepository,
                              BillService billService) {
        this.accountRepository = accountRepository;
        this.billService = billService;
    }

    @Override
    public Account save(Account account) {
        if (account.getMoney() == null) {
            account.setMoney(BigDecimal.valueOf(0));
        }
        return accountRepository.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findAccountsByUserId(Long id) {
        return accountRepository.findAccountsByUserId(id);
    }

    @Override
    public Account addBillToAccount(Bill bill, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Can't find account by id " + accountId));
        billService.save(bill);
        account.setMoney(account.getMoney().add(bill.getCount()));
        return accountRepository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find account by id " + id));
    }
}

package bank.atm.service;

import bank.atm.model.Account;
import bank.atm.model.Bill;
import java.util.List;

public interface AccountService {
    Account save(Account account);

    List<Account> findAll();

    List<Account> findAccountsByUserId(Long id);

    Account addBillToAccount(Bill bill, Long accountId);

    Account findById(Long id);
}

package bank.atm.service;

import bank.atm.model.Bill;
import bank.atm.model.User;

public interface UserService {
    User save(User user);

    User findById(Long id);

    User topUpAccount(Bill bill, Long accountId, Long userId);

    User sendFromAccountToAccount(Long accountFromId, Long accountToId, int sum);

    User getMoneyFromAccount(Long accountId, int sum);
}

package bank.atm.service.impl;

import bank.atm.model.Account;
import bank.atm.model.Bill;
import bank.atm.model.User;
import bank.atm.repository.UserRepository;
import bank.atm.service.AccountService;
import bank.atm.service.BillService;
import bank.atm.service.UserService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final int BILL_CRITERIA = 100;
    private static final int FIVE_HUNDRED = 500;
    private static final int TWO_HUNDRED = 200;
    private static final int ONE_HUNDRED = 100;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final BillService billService;

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
        account.setMoney(account.getMoney().add(bill.getCount()));
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
        accountFrom.setMoney(accountFrom.getMoney().subtract(BigDecimal.valueOf(sum)));
        Account accountTo = accountService.findById(accountToId);
        accountTo.setMoney(accountTo.getMoney().add(BigDecimal.valueOf(sum)));
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
        final User user = userRepository.findById(account.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Can't find user by id "
                        + account.getUser().getId()));
        account.setMoney(account.getMoney().subtract(BigDecimal.valueOf(sum)));
        deleteBillsFromAtm(sum);
        accountService.save(account);
        return user;
    }

    @Override
    public User addBillToAtm(User user, List<Bill> bills) {
        bills.stream().map(billService::save).collect(Collectors.toList());
        return user;
    }

    private void checkBill(int sum) {
        List<Bill> bills = billService.findAll();
        int count = 0;
        for (int i = 0; i < bills.size(); i++) {
            count += bills.get(i).getCount().intValue();
        }
        if (sum % BILL_CRITERIA != 0 || sum <= 0 || count < sum) {
            throw new RuntimeException("Incorrect sum or ATM have no money for now");
        }
    }

    private void checkAccount(Account account, int sum) {
        if (account.getMoney().intValue() < sum) {
            throw new RuntimeException("You have not enough money on your account!");
        }
    }

    private void deleteBillsFromAtm(int sum) {
        List<Bill> fiveBills = billService.findByCount(FIVE_HUNDRED);
        List<Bill> twoBills = billService.findByCount(TWO_HUNDRED);
        List<Bill> oneBills = billService.findByCount(ONE_HUNDRED);

        int fiveBill = sum / FIVE_HUNDRED;
        if (fiveBill > fiveBills.size()) {
            fiveBill = fiveBills.size();
        }
        sum = sum - (fiveBill * FIVE_HUNDRED);
        billService.deleteTopByOrderByIdAsc(fiveBill, FIVE_HUNDRED);
        if (sum > 0) {
            int twoBill = sum / TWO_HUNDRED;
            if (twoBill > twoBills.size()) {
                twoBill = twoBills.size();
            }
            sum = sum - (twoBill * TWO_HUNDRED);
            billService.deleteTopByOrderByIdAsc(twoBill, TWO_HUNDRED);
        }
        if (sum > 0) {
            int oneBill = sum / ONE_HUNDRED;
            if (oneBill > oneBills.size()) {
                throw new RuntimeException("ATM is out of bills!");
            }
            billService.deleteTopByOrderByIdAsc(oneBill, ONE_HUNDRED);
        }
    }
}

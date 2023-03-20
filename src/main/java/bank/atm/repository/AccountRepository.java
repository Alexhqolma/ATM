package bank.atm.repository;

import bank.atm.model.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountsByUserId(Long id);
}

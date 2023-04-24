package bank.atm.repository;

import bank.atm.model.Bill;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByCount(long count);

    default void deleteBills(int count) {
        PageRequest pageRequest = PageRequest.of(0, count);
        List<Bill> bills = findAll(pageRequest).getContent();
        deleteAll(bills);
    }
}

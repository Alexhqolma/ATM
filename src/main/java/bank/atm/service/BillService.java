package bank.atm.service;

import bank.atm.model.Bill;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface BillService {
    Bill save(Bill bill);

    List<Bill> findAll();

    List<Bill> findAll(PageRequest pageRequest);

    Bill findById(Long id);

    List<Bill> findByCount(long count);

    void delete(Bill bill);

    void deleteTopByOrderByIdAsc(int limit, int billCount);
}

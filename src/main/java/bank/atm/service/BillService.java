package bank.atm.service;

import bank.atm.model.Bill;
import java.util.List;

public interface BillService {
    Bill save(Bill bill);

    List<Bill> findAll();

    Bill findById(Long id);
}

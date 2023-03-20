package bank.atm.service.impl;

import bank.atm.model.Bill;
import bank.atm.repository.BillRepository;
import bank.atm.service.BillService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;

    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find bill bt id " + id));
    }
}
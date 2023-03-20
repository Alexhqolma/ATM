package bank.atm.service.impl;

import bank.atm.model.Bill;
import bank.atm.model.Storage;
import bank.atm.repository.StorageRepository;
import bank.atm.service.StorageService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    private final StorageRepository storageRepository;

    public StorageServiceImpl(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Override
    public Storage save(Storage storage) {
        return storageRepository.save(storage);
    }

    @Override
    public Storage findById(Long id) {
        return storageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find storage by id " + id));
    }

    public Storage addBills(Storage storage) {
        Storage storageDb = storageRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Can't find storage"));
        List<Bill> storageBills = storageDb.getBills();
        storageBills.addAll(storage.getBills());
        storage.setBills(storageBills);
        return storageRepository.save(storage);
    }
}

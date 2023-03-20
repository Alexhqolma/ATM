package bank.atm.service;

import bank.atm.model.Storage;

public interface StorageService {
    Storage save(Storage storage);

    Storage findById(Long id);

    Storage addBills(Storage storage);
}

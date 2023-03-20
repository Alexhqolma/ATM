package bank.atm.dto.mapper;

import bank.atm.dto.request.StorageRequestDto;
import bank.atm.dto.response.StorageResponseDto;
import bank.atm.model.Bill;
import bank.atm.model.Storage;
import bank.atm.service.BillService;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class StorageMapper {
    private final BillService billService;

    public StorageMapper(BillService billService) {
        this.billService = billService;
    }

    public Storage toModel(StorageRequestDto dto) {
        Storage storage = new Storage();
        storage.setBills(dto.getBillsId().stream()
                .map(billService::findById)
                .collect(Collectors.toList()));
        return storage;
    }

    public StorageResponseDto toDto(Storage storage) {
        StorageResponseDto dto = new StorageResponseDto();
        dto.setId(storage.getId());
        dto.setBillsId(storage.getBills().stream()
                .map(Bill::getId)
                .collect(Collectors.toList()));
        return dto;
    }

}

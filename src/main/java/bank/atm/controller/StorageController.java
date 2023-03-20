package bank.atm.controller;

import bank.atm.dto.mapper.StorageMapper;
import bank.atm.dto.request.StorageRequestDto;
import bank.atm.dto.response.StorageResponseDto;
import bank.atm.model.Storage;
import bank.atm.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storage")
public class StorageController {
    private final StorageService storageService;
    private final StorageMapper storageMapper;

    public StorageController(StorageService storageService,
                             StorageMapper storageMapper) {
        this.storageService = storageService;
        this.storageMapper = storageMapper;
    }

    @PostMapping("/create")
    public StorageResponseDto createStorage(@RequestBody StorageRequestDto storageRequestDto) {
        Storage storage = storageService.save(storageMapper.toModel(storageRequestDto));
        return storageMapper.toDto(storage);
    }

    @GetMapping
    public StorageResponseDto getStorage() {
        return storageMapper.toDto(storageService.findById(1L));
    }

    @PostMapping("/add-bills")
    public StorageResponseDto addBills(@RequestBody StorageRequestDto storageRequestDto) {
        Storage storage = storageService.addBills(storageMapper.toModel(storageRequestDto));
        return storageMapper.toDto(storage);
    }
}

package bank.atm.controller;

import bank.atm.dto.mapper.BillMapper;
import bank.atm.dto.request.BillRequestDto;
import bank.atm.dto.response.BillResponseDto;
import bank.atm.model.Bill;
import bank.atm.service.BillService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
public class BillController {
    private final BillService billService;
    private final BillMapper billMapper;

    public BillController(BillService billService,
                          BillMapper billMapper) {
        this.billService = billService;
        this.billMapper = billMapper;
    }

    @PostMapping("/create")
    public BillResponseDto createBill(@RequestBody BillRequestDto billRequestDto) {
        Bill bill = billService.save(billMapper.toModel(billRequestDto));
        return billMapper.toDto(bill);
    }

    @GetMapping
    public List<BillResponseDto> getAllBills() {
        return billService.findAll()
                .stream()
                .map(billMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BillResponseDto getBillById(@PathVariable Long id) {
        return billMapper.toDto(billService.findById(id));
    }

}

package bank.atm.controller;

import bank.atm.dto.mapper.AccountMapper;
import bank.atm.dto.mapper.BillMapper;
import bank.atm.dto.mapper.UserMapper;
import bank.atm.dto.request.BillRequestDto;
import bank.atm.dto.request.UserRequestDto;
import bank.atm.dto.response.AccountResponseDto;
import bank.atm.dto.response.BillResponseDto;
import bank.atm.dto.response.UserResponseDto;
import bank.atm.model.Bill;
import bank.atm.model.User;
import bank.atm.service.AccountService;
import bank.atm.service.BillService;
import bank.atm.service.UserService;
import java.util.ArrayList;
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
    private final UserMapper userMapper;
    private final UserService userService;
    private final AccountMapper accountMapper;
    private final AccountService accountService;

    public BillController(BillService billService,
                          BillMapper billMapper,
                          UserMapper userMapper,
                          UserService userService,
                          AccountMapper accountMapper,
                          AccountService accountService) {
        this.billService = billService;
        this.billMapper = billMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.accountMapper = accountMapper;
        this.accountService = accountService;
    }

    @PostMapping("/add")
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

    @PostMapping("/count")
    public int countBills(long count) {
        return billService.findByCount(count).size();
    }

    @PostMapping("/add-bill")
    public UserResponseDto addBillToAtm(@RequestBody UserRequestDto userRequestDto,
                                        List<BillRequestDto> bills) {
        User user = userMapper.toModel(userRequestDto);
        List<Bill> newBills = new ArrayList<>();
        for (BillRequestDto bill : bills) {
            Bill toModel = billMapper.toModel(bill);
            newBills.add(toModel);
        }
        return userMapper.toDto(userService.addBillToAtm(user, newBills));
    }

    @PostMapping("/add-bill/{id}")
    public AccountResponseDto addBillToAccount(@RequestBody BillRequestDto billRequestDto,
                                               @PathVariable Long id) {
        Bill bill = billMapper.toModel(billRequestDto);

        return accountMapper.toDto(accountService.addBillToAccount(bill, id));
    }

}

package bank.atm.dto.mapper;

import bank.atm.dto.request.BillRequestDto;
import bank.atm.dto.response.BillResponseDto;
import bank.atm.model.Bill;
import org.springframework.stereotype.Component;

@Component
public class BillMapper {
    public Bill toModel(BillRequestDto billRequestDto) {
        Bill bill = new Bill();
        bill.setCount(billRequestDto.getCount());
        return bill;
    }

    public BillResponseDto toDto(Bill bill) {
        BillResponseDto dto = new BillResponseDto();
        dto.setId(bill.getId());
        dto.setCount(bill.getCount());
        return dto;
    }
}

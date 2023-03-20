package bank.atm.dto.request;

import java.util.List;

public class StorageRequestDto {
    private List<Long> billsId;

    public List<Long> getBillsId() {
        return billsId;
    }
}

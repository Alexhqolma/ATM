package bank.atm.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class StorageResponseDto {
    private Long id;

    private List<Long> billsId;
}

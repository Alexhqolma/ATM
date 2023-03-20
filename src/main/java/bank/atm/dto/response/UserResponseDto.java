package bank.atm.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String login;
    private String password;
    private String role;
    private List<Long> accountsId;
}

package bank.atm.dto.request;

import jakarta.validation.constraints.Size;
import java.util.List;

public class UserRequestDto {
    @Size(min = 5, max = 20, message
            = "Login must be between 5 and 20 characters")
    private String login;
    @Size(min = 8, max = 20, message
            = "Password must be between 8 and 20 characters")
    private String password;
    private String role;
    private List<Long> accountsId;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public List<Long> getAccountsId() {
        return accountsId;
    }
}

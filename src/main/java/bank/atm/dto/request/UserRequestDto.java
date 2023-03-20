package bank.atm.dto.request;

import java.util.List;

public class UserRequestDto {
    private String login;
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

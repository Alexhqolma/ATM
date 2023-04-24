package bank.atm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 5, max = 20, message
            = "Login must be between 5 and 20 characters")
    private String login;
    @Size(min = 8, max = 20, message
            = "Password must be between 8 and 20 characters")
    private String password;
    private String role;
    @OneToMany
    private List<Account> accounts;
}

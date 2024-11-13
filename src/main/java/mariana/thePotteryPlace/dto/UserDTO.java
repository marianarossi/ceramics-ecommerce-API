package mariana.thePotteryPlace.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User can't be null.")
    @Size(min = 4, max = 50)
    private String email;

    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @NotNull
    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;

    @NotNull
    @Size(min = 9) //max?
    private String ssn;

    @NotNull
    private Date birthDate;

    @NotNull
    private String gender;

    @NotNull
    private String phone;

}

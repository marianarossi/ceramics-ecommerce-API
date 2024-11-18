package mariana.thePotteryPlace.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor //precisa?
public class UserDTO {
    @NotNull(message = "User can't be null.")
    @Size(min = 4, max = 50)
    private String email;

    @NotNull
    @Size(min = 4, max = 50)
    private String displayName;

    @NotNull
    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;

    @NotNull
    @Size(min = 9) //max?
    private String ssn;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String gender;

    @NotNull
    private String phone;
}

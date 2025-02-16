package mariana.thePotteryPlace.dto.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    @Size(min = 4, max = 50)
    private String email;

    @Size(min = 4, max = 50)
    private String displayName;

    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;

    @Size(min = 9) //max?
    private String ssn;

    private LocalDate birthDate;

    private String gender;

    private String phone;
}

package mariana.thePotteryPlace.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mariana.thePotteryPlace.validation.CreateGroup;
import mariana.thePotteryPlace.validation.UpdateGroup;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull(message = "User can't be null.", groups = CreateGroup.class)
    @Size(min = 4, max = 50, groups = {CreateGroup.class, UpdateGroup.class})
    private String email;

    @NotNull(message = "Display name is required.", groups = CreateGroup.class)
    @Size(min = 4, max = 50, groups = {CreateGroup.class, UpdateGroup.class})
    private String displayName;

    @NotNull(message = "Password is required.", groups = CreateGroup.class)
    @Size(min = 6, groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", groups = {CreateGroup.class, UpdateGroup.class})
    private String password;

    @NotNull(message = "SSN is required.", groups = CreateGroup.class)
    @Size(min = 9, groups = {CreateGroup.class, UpdateGroup.class})
    private String ssn;

    @NotNull(message = "Birth date is required.", groups = CreateGroup.class)
    private LocalDate birthDate;

    @NotNull(message = "Gender is required.", groups = CreateGroup.class)
    private String gender;

    @NotNull(message = "Phone is required.", groups = CreateGroup.class)
    private String phone;
}

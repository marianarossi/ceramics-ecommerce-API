package mariana.thePotteryPlace.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ResponseUserDTO {
    private Long id;

    private String email;

    private String displayName;

    private LocalDate birthDate;

    private String ssn;

    private String gender;

    private String phone;
}

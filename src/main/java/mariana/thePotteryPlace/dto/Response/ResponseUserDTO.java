package mariana.thePotteryPlace.dto.Response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ResponseUserDTO {

    private String email;

    private String displayName;

    private LocalDate birthDate;

    private String gender;

    private String phone;
}

package mariana.thePotteryPlace.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mariana.thePotteryPlace.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor //precisa?
public class AddressDTO {
    private Long id;

    @NotNull
    private String street;

    @NotNull
    private int number;

    @NotNull
    private String complement;

    @NotNull
    private String neighborhood;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String country;

    @NotNull
    private String zip;

    //anotacao do banco aqui?
    private User user;
}

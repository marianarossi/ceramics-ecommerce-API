package mariana.thePotteryPlace.dto.response;

import lombok.Data;

@Data
public class ResponseAddressDTO {
    private Long id;

    private String street;

    private int number;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    private String country;

    private String zip;

}

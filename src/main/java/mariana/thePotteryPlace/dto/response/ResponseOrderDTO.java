package mariana.thePotteryPlace.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data

public class ResponseOrderDTO {
    private Long id;

    private LocalDateTime date;

//    private Long userId;

    private BigDecimal shipping;

    private String status;

    private String payment;

    private List<ResponseOrderItemDTO> items; // Explicitly include order items here

}

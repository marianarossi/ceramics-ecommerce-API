package mariana.thePotteryPlace.dto.Response;

import lombok.Data;
import mariana.thePotteryPlace.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data

public class ResponseOrderDTO {
    private LocalDateTime date;

//    private Long userId;

    private BigDecimal shipping;

    private String status;

    private String payment;

    private List<ResponseOrderItemDTO> items; // Explicitly include order items here

}

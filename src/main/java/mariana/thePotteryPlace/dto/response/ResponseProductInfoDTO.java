package mariana.thePotteryPlace.dto.response;

import lombok.Data;
import mariana.thePotteryPlace.model.Category;

import java.math.BigDecimal;

@Data
public class ResponseProductInfoDTO {
    private Long id;

    private String title;

    private String text;

    private String img;

    private BigDecimal price;

    private String installment;

    private String color;

    private BigDecimal height;

    private BigDecimal width;

    private String recommended_environment;

    private String recommended_for_plants;

    private String img1;

    private String img2;

    private String img3;

    private Category category;
}

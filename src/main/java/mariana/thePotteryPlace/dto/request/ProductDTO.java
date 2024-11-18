package mariana.thePotteryPlace.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mariana.thePotteryPlace.model.Category;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor //precisa?
public class ProductDTO {
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String title;

    @NotNull
    private String text;

    @NotNull
    private String img;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String installment;

    @NotNull
    private String color;

    @NotNull
    private BigDecimal height;

    @NotNull
    private BigDecimal width;

    @NotNull
    private String recommended_environment;

    @NotNull
    private String recommended_for_plants;

    @NotNull
    private String img1;

    @NotNull
    private String img2;

    @NotNull
    private String img3;

    private Category category;
}

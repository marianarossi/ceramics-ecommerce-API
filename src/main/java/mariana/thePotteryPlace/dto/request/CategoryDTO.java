package mariana.thePotteryPlace.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor //precisa?
public class CategoryDTO {
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;
}

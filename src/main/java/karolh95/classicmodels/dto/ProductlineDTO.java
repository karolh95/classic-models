package karolh95.classicmodels.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductlineDTO {

    @Schema(description = "Unique identifier ot the Productline", example = "Classic Cars")
    private String productLine;
    @Schema(description = "Text description of the Productline", example = "Classic cars text description")
    private String textDescription;
    @Schema(description = "Html description of the Productline", example = "<span> Classic cars HTML description</span>")
    private String htmlDescription;
    @JsonIgnore
    private byte[] image;
}

package karolh95.classicmodels.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class ProductlineDTO {

    @NotEmpty(message = "{productline.productLine.not-empty}")
    @Size(max = 50, message = "{productline.productLine.length}")
    @Schema(description = "Unique identifier ot the Productline", example = "Classic Cars")
    private String productLine;

    @NotEmpty(message = "{productline.textDescription.not-empty}")
    @Size(max = 4000, message = "{productline.textDescription.length}")
    @Schema(description = "Text description of the Productline", example = "Classic cars text description")
    private String textDescription;

    @NotEmpty(message = "{productline.htmlDescription.not-empty}")
    @Schema(description = "Html description of the Productline", example = "<span> Classic cars HTML description</span>")
    private String htmlDescription;

    @JsonIgnore
    private byte[] image;
}

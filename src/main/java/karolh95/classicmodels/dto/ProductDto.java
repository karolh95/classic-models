package karolh95.classicmodels.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    @NotEmpty(message = "{product.productCode.not-empty}")
    @Size(max = 15,message = "{product.productCode.length}")
    private String productCode;

    @NotEmpty(message = "{product.productName.not-empty}")
    @Size(max = 70, message = "{product.productName.length}")
    private String productName;

    private String productLine;

    @NotEmpty(message = "{product.productScale.not-empty}")
    @Size(max = 10, message = "{product.productScale.length}")
    private String productScale;

    @NotEmpty(message = "{product.productVendor.not-empty}")
    @Size(max = 50, message = "{product.productVendor.length}")
    private String productVendor;

    @NotEmpty(message = "{product.productDescription.not-empty}")
    private String productDescription;

    @NotNull(message = "{product.quantityInStock.not-null}")
    private Short quantityInStock;

    @NotNull(message = "{product.buyPrice.not-null}")
    @Digits(integer = 10, fraction = 2, message = "{product.buyPrice.digits}")
    private BigDecimal buyPrice;

    @NotNull(message = "{product.MSRP.not-null}")
    @Digits(integer = 10, fraction = 2, message = "{product.MSRP.digits}")
    private BigDecimal MSRP;
}

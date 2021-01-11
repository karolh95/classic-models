package karolh95.classicmodels.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private String productCode;

    private String productName;

    private String productLine;

    private String productScale;

    private String productVendor;

    private String productDescription;

    private Short quantityInStock;

    private BigDecimal buyPrice;

    private BigDecimal MSRP;
}

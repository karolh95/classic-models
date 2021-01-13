package karolh95.classicmodels.utils;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.models.Product;
import karolh95.classicmodels.models.Productline;
import org.mockito.ArgumentMatchers;

public class ArgumentMatchersAdapter {

    public static Productline anyProductline() {
        return ArgumentMatchers.any(Productline.class);
    }

    public static ProductlineDTO anyProductlineDto() {
        return ArgumentMatchers.any(ProductlineDTO.class);
    }

    public static Product anyProduct() {
        return ArgumentMatchers.any(Product.class);
    }

    public static ProductDto anyProductDto() {
        return ArgumentMatchers.any(ProductDto.class);
    }

}

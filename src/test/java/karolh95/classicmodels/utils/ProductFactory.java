package karolh95.classicmodels.utils;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.models.Product;

import java.math.BigDecimal;

public class ProductFactory {

    private static final String PRODUCT_CODE = "S12_1099";
    private static final String PRODUCT_NAME = "1968 Ford Mustang";
    private static final String PRODUCT_LINE = "Classic Cars";
    private static final String PRODUCT_SCALE = "1:12";
    private static final String PRODUCT_VENDOR = "Autoart Studio Design";
    private static final String PRODUCT_DESCRIPTION = "Hood, doors and trunk all open to reveal highly detailed interior features. Steering wheel actually turns the front wheels. Color dark green.";
    private static final Short QUANTITY_IN_STOCK = Short.parseShort("68");
    private static final BigDecimal BUY_PRICE = BigDecimal.valueOf(95.34);
    private static final BigDecimal MSRP = BigDecimal.valueOf(194.57);

    public static Product getProduct() {

        Product product = new Product();

        product.setProductCode(PRODUCT_CODE);
        product.setProductName(PRODUCT_NAME);
        product.setProductLine(PRODUCT_LINE);
        product.setProductScale(PRODUCT_SCALE);
        product.setProductVendor(PRODUCT_VENDOR);
        product.setProductDescription(PRODUCT_DESCRIPTION);
        product.setQuantityInStock(QUANTITY_IN_STOCK);
        product.setBuyPrice(BUY_PRICE);
        product.setMSRP(MSRP);

        return product;
    }

    public static ProductDto getProductDto() {

        ProductDto productDto = new ProductDto();
        productDto.setProductCode(PRODUCT_CODE);
        productDto.setProductName(PRODUCT_NAME);
        productDto.setProductLine(PRODUCT_LINE);
        productDto.setProductScale(PRODUCT_SCALE);
        productDto.setProductVendor(PRODUCT_VENDOR);
        productDto.setProductDescription(PRODUCT_DESCRIPTION);
        productDto.setQuantityInStock(QUANTITY_IN_STOCK);
        productDto.setBuyPrice(BUY_PRICE);
        productDto.setMSRP(MSRP);

        return productDto;
    }
}

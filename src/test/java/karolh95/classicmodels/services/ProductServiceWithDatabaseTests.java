package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.exceptions.ProductAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductNotFoundException;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/drop_tables.sql", "/productlines.sql", "/products.sql"})
@DisplayName("Product Service with database tests")
public class ProductServiceWithDatabaseTests {

    private static final int PRODUCTS_NUMBER = 38;
    private static final String PRODUCT_LINE = "Classic Cars";
    private static final String PRODUCT_CODE = "S12_1099";
    private static final String NON_EXISTING_PRODUCT_CODE = "XXXXXX";
    private static final String NON_EXISTING_PRODUCT_LINE = "Classic Mini Cars";

    private static final Pageable PAGEABLE = PageRequest.of(0, 10);

    @Autowired
    private ProductService productService;

    @Test
    public void findProductByProductCodeTest() {

        ProductDto product = productService.findProductByProductCode(PRODUCT_CODE);

        assertNotNull(product);
        assertEquals(PRODUCT_CODE, product.getProductCode());
    }

    @Test
    public void findProductByProductCode_productNotFoundTest() {

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.findProductByProductCode(NON_EXISTING_PRODUCT_CODE)
        );
    }

    @Test
    public void findAllProductsTest() {

        Page<ProductDto> products = productService.findAllProducts(PRODUCT_LINE, PAGEABLE);

        assertNotNull(products);
        assertEquals(PRODUCTS_NUMBER, products.getTotalElements());
    }

    @Test
    public void saveProductTest() {

        ProductDto product = new ProductDto();
        product.setProductCode(NON_EXISTING_PRODUCT_CODE);

        product = productService.saveProduct(PRODUCT_LINE, product);

        assertNotNull(product);
        assertEquals(NON_EXISTING_PRODUCT_CODE, product.getProductCode());
    }

    @Test
    public void saveProduct_productLineNotFoundTest() {

        ProductDto product = new ProductDto();
        product.setProductCode(NON_EXISTING_PRODUCT_CODE);

        assertThrows(
                ProductlineNotFoundException.class,
                () -> productService.saveProduct(NON_EXISTING_PRODUCT_LINE, product)
        );
    }

    @Test
    public void saveProduct_productAlreadyExistsTest() {

        ProductDto product = new ProductDto();
        product.setProductCode(PRODUCT_CODE);

        assertThrows(
                ProductAlreadyExistsException.class,
                () -> productService.saveProduct(PRODUCT_LINE, product)
        );
    }

    @Test
    public void updateProductTest() {

        String newProductName = "new product name";

        ProductDto product = new ProductDto();
        product.setProductCode(PRODUCT_CODE);
        product.setProductName(newProductName);

        product = productService.updateProduct(PRODUCT_CODE, product);

        assertNotNull(product);
        assertEquals(newProductName, product.getProductName());
    }

    @Test
    public void updateProduct_productNotFoundTest() {

        ProductDto product = new ProductDto();
        product.setProductCode(NON_EXISTING_PRODUCT_CODE);

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.updateProduct(NON_EXISTING_PRODUCT_CODE, product)
        );
    }

    @Test
    public void deleteProductTest() {

        ProductDto product = productService.findProductByProductCode(PRODUCT_CODE);

        assertNotNull(product);

        productService.deleteProduct(PRODUCT_CODE);

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.findProductByProductCode(PRODUCT_CODE)
        );
    }

    @Test
    public void deleteProduct_productNotFoundTest() {

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.deleteProduct(NON_EXISTING_PRODUCT_CODE)
        );
    }
}

package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.exceptions.ProductAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.models.Product;
import karolh95.classicmodels.repositories.ProductRepository;
import karolh95.classicmodels.utils.ProductFactory;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Product Service Tests")
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductlineService productlineService;

    @MockBean
    private ProductRepository productRepository;

    @Nested
    @DisplayName("saveProduct")
    class SaveProductTests {

        @Test
        public void saveProductTest() {

            Product product = ProductFactory.getProduct();

            doReturn(false)
                    .when(productRepository)
                    .existsById(anyString());

            doAnswer(invocation -> {
                Product newProduct = invocation.getArgument(1);
                newProduct.setProductline(ProductlineFactory.getProductline());
                return null;
            }).when(productlineService)
                    .setProductline(anyString(), any(Product.class));

            doReturn(product)
                    .when(productRepository)
                    .save(any(Product.class));

            ProductDto productDto = ProductFactory.getProductDto();

            productDto = productService.saveProduct("productLine", productDto);

            assertNotNull(productDto);
            assertEquals(productDto.getProductCode(), product.getProductCode());
            assertEquals(productDto.getProductName(), product.getProductName());

        }

        @Test
        public void saveProduct_productAlreadyExistsTest() {

            ProductDto productDto = ProductFactory.getProductDto();
            doReturn(true)
                    .when(productRepository)
                    .existsById(anyString());

            assertThrows(
                    ProductAlreadyExistsException.class,
                    () -> productService.saveProduct("productLine", productDto)
            );
        }

        @Test
        public void saveProduct_productlineNotFoundTest() {

            doReturn(false)
                    .when(productRepository)
                    .existsById(anyString());

            doThrow(ProductlineNotFoundException.class)
                    .when(productlineService)
                    .setProductline(anyString(), any(Product.class));

            ProductDto productDto = ProductFactory.getProductDto();

            assertThrows(
                    ProductlineNotFoundException.class,
                    () -> productService.saveProduct("productLine", productDto)
            );
        }
    }
}

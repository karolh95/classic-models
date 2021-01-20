package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.exceptions.ProductAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductNotFoundException;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.mappers.ProductMapper;
import karolh95.classicmodels.models.Product;
import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.repositories.ProductRepository;
import karolh95.classicmodels.utils.ProductFactory;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static karolh95.classicmodels.utils.ArgumentMatchersAdapter.anyProduct;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Service Tests")
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductlineService productlineService;

    @Mock
    private ProductRepository productRepository;

    @Spy
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Nested
    @DisplayName("saveProduct")
    class SaveProductTests {

        @Test
        public void saveProductTest() {

            Product product = ProductFactory.getProduct();
            Productline productline = ProductlineFactory.getProductline();
            doReturn(false)
                    .when(productRepository)
                    .existsById(anyString());

            doReturn(productline)
                    .when(productlineService)
                    .findByProductline(anyString());

            doReturn(product)
                    .when(productRepository)
                    .save(anyProduct());

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
                    .findByProductline(anyString());

            ProductDto productDto = ProductFactory.getProductDto();

            assertThrows(
                    ProductlineNotFoundException.class,
                    () -> productService.saveProduct("productLine", productDto)
            );
        }
    }

    @Nested
    @DisplayName("updateProduct")
    class UpdateProductTests {

        @Test
        public void updateProductTest() {

            String newProductName = "new product name";
            Product product = ProductFactory.getProduct();
            product.setProductName(newProductName);

            doReturn(Optional.of(ProductFactory.getProduct()))
                    .when(productRepository)
                    .findById(anyString());
            doReturn(product)
                    .when(productRepository)
                    .save(anyProduct());

            ProductDto productDto = ProductFactory.getProductDto();
            productDto.setProductName(newProductName);

            productDto = productService.updateProduct("productCode", productDto);

            assertNotNull(productDto);
            assertEquals(newProductName, productDto.getProductName());
        }

        @Test
        public void updateProduct_productNotFoundTest() {

            ProductDto productDto = ProductFactory.getProductDto();
            doReturn(Optional.empty())
                    .when(productRepository)
                    .findById(anyString());

            assertThrows(
                    ProductNotFoundException.class,
                    () -> productService.updateProduct("productCode", productDto)
            );
        }
    }

    @Nested
    @DisplayName("findProductByProductCode")
    class FindProductByProductCodeTests {

        @Test
        public void findProductByProductCodeTest() {

            doReturn(Optional.of(ProductFactory.getProduct()))
                    .when(productRepository)
                    .findById(anyString());

            ProductDto productDto = productService.findProductByProductCode("productCode");

            assertNotNull(productDto);
        }

        @Test
        public void findProductByProductCode_productNotFoundTest() {

            doReturn(Optional.empty())
                    .when(productRepository)
                    .findById(anyString());

            assertThrows(
                    ProductNotFoundException.class,
                    () -> productService.findProductByProductCode("productCode")
            );
        }
    }

    @Nested
    @DisplayName("deleteProduct")
    class DeleteProductTests {

        @Test
        public void deleteProductTest() {

            doReturn(Optional.of(ProductFactory.getProduct()))
                    .when(productRepository)
                    .findById(anyString());

            productService.deleteProduct("productCode");

            verify(productRepository, times(1))
                    .delete(anyProduct());
        }

        @Test
        public void deleteProduct_productNotFoundTest() {

            doReturn(Optional.empty())
                    .when(productRepository)
                    .findById(anyString());

            assertThrows(
                    ProductNotFoundException.class,
                    () -> productService.deleteProduct("productCode")
            );
        }
    }
}

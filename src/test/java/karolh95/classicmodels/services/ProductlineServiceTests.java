package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.exceptions.ProductlineAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.mappers.ProductlineMapper;
import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.repositories.ProductlineRepository;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.jupiter.api.BeforeEach;
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

import static karolh95.classicmodels.utils.ArgumentMatchersAdapter.anyProductline;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Productline Service Tests")
public class ProductlineServiceTests {

    @InjectMocks
    private ProductlineService productlineService;

    @Mock
    private ProductlineRepository productlineRepository;

    @Spy
    private final ProductlineMapper mapper = Mappers.getMapper(ProductlineMapper.class);

    @Nested
    @DisplayName("saveProductline")
    class SaveProductlineTests {

        private ProductlineDTO dto;

        @BeforeEach
        public void setUp() {
            dto = ProductlineFactory.getPoductlineDto();
        }

        @Test
        public void saveProductlineTest() {

            Productline productline = ProductlineFactory.getProductline();
            doReturn(false)
                    .when(productlineRepository)
                    .existsById(anyString());
            doReturn(productline)
                    .when(productlineRepository)
                    .save(anyProductline());

            dto = productlineService.saveProductline(dto);

            assertNotNull(dto);
            assertEquals(productline.getProductLine(), dto.getProductLine());
            assertEquals(productline.getTextDescription(), dto.getTextDescription());
            assertEquals(productline.getHtmlDescription(), dto.getHtmlDescription());
            assertArrayEquals(productline.getImage(), dto.getImage());
        }

        @Test
        public void saveProductline_productlineAlreadyExistsTest() {

            doReturn(true)
                    .when(productlineRepository)
                    .existsById(anyString());

            assertThrows(
                    ProductlineAlreadyExistsException.class,
                    () -> productlineService.saveProductline(dto)
            );
        }

    }

    @Nested
    @DisplayName("updateProductline")
    class UpdateProductlineTests {

        private ProductlineDTO dto;

        @BeforeEach
        public void setUp() {
            dto = ProductlineFactory.getPoductlineDto();
        }


        @Test
        public void updateProductlineTest() {

            final String newTEXT = "new text description";
            final String newHTML = "new HTML description";
            final byte[] newIMAGE = new byte[5];

            Productline productline = ProductlineFactory.getProductline();
            productline.setTextDescription(newTEXT);
            productline.setHtmlDescription(newHTML);
            productline.setImage(newIMAGE);

            doReturn(Optional.of(ProductlineFactory.getProductline()))
                    .when(productlineRepository)
                    .findById(anyString());
            doReturn(productline)
                    .when(productlineRepository)
                    .save(anyProductline());

            dto.setTextDescription(newTEXT);
            dto.setHtmlDescription(newHTML);
            dto.setImage(newIMAGE);

            ProductlineDTO result = productlineService.updateProductline(dto.getProductLine(), dto);

            assertNotNull(dto);
            assertEquals(dto.getTextDescription(), result.getTextDescription());
            assertEquals(dto.getHtmlDescription(), result.getHtmlDescription());
            assertArrayEquals(dto.getImage(), result.getImage());
        }

        @Test
        public void updateProductline_productlineNotFoundTest() {

            doReturn(Optional.empty())
                    .when(productlineRepository)
                    .findById(anyString());

            assertThrows(
                    ProductlineNotFoundException.class,
                    () -> productlineService.updateProductline(dto.getProductLine(), dto)
            );
        }
    }

    @Nested
    @DisplayName("findProductline")
    class FindProductlineTests {

        @Test
        public void findProductlineTest() {

            ProductlineDTO dto = ProductlineFactory.getPoductlineDto();
            doReturn(Optional.of(ProductlineFactory.getProductline()))
                    .when(productlineRepository)
                    .findById(anyString());

            assertDoesNotThrow(
                    () -> productlineService.findProductlineByProductLine(dto.getProductLine())
            );
        }

        @Test
        public void findProductline_ProductlineNotFoundTest() {

            doReturn(Optional.empty())
                    .when(productlineRepository)
                    .findById(anyString());

            assertThrows(
                    ProductlineNotFoundException.class,
                    () -> productlineService.findProductlineByProductLine("productLine")
            );
        }
    }

    @Nested
    @DisplayName("findImage")
    class FindImageTests {

        @Test
        public void findImageTest() {

            Productline productline = ProductlineFactory.getProductline();

            doReturn(Optional.of(productline))
                    .when(productlineRepository)
                    .findById(anyString());

            assertDoesNotThrow(() -> {
                byte[] image = productlineService.getImageByProductLine("productLine");

                assertArrayEquals(productline.getImage(), image);
            });
        }

        @Test
        public void findImage_productlineNotFoundTest() {

            doThrow(ProductlineNotFoundException.class)
                    .when(productlineRepository)
                    .findById(anyString());

            assertThrows(
                    ProductlineNotFoundException.class,
                    () -> productlineService.getImageByProductLine("productLine")
            );
        }
    }

    @Nested
    @DisplayName("deleteProductline")
    class DeleteProductlineTests {

        @Test
        public void deleteProductlineTest() {

            Productline productline = ProductlineFactory.getProductline();
            doReturn(Optional.of(productline))
                    .when(productlineRepository)
                    .findById(anyString());

            productlineService.deleteProductline(productline.getProductLine());

            verify(productlineRepository, times(1))
                    .delete(anyProductline());
        }

        @Test
        public void deleteProductline_productlineNotFoundTest() {

            doReturn(Optional.empty())
                    .when(productlineRepository)
                    .findById(anyString());

            assertThrows(
                    ProductlineNotFoundException.class,
                    () -> productlineService.deleteProductline("productLine")
            );
        }
    }

    @Nested
    @DisplayName("findByProductLine")
    class FindByProductLineTests {

        @Test
        public void findByProductLineTest() {

            Productline productline = ProductlineFactory.getProductline();
            doReturn(Optional.of(productline))
                    .when(productlineRepository)
                    .findById(anyString());

            productline = productlineService.findByProductline("productLine");

            assertNotNull(productline);
        }

        @Test
        public void findByProductLine_productlineNotFoundTest() {

            doReturn(Optional.empty())
                    .when(productlineRepository)
                    .findById(anyString());

            assertThrows(
                    ProductlineNotFoundException.class,
                    () -> productlineService.findByProductline("prductLine")
            );
        }
    }
}

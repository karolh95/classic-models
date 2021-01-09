package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.exceptions.ProductlineAlreadyExists;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.repositories.ProductlineRepository;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductlineServiceTests {

    @Autowired
    private ProductlineService productlineService;

    @MockBean
    private ProductlineRepository productlineRepository;

    private ProductlineDTO dto;

    @Before
    public void setUp() {
        dto = ProductlineFactory.getPoductlineDto();
    }

    @Test
    public void shouldSaveProductline() {

        Productline productline = ProductlineFactory.getProductline();
        doReturn(false)
                .when(productlineRepository)
                .existsById(anyString());
        doReturn(productline)
                .when(productlineRepository)
                .save(any(Productline.class));

        dto = productlineService.saveProductline(dto);

        assertNotNull(dto);
        assertEquals(productline.getProductLine(), dto.getProductLine());
        assertEquals(productline.getTextDescription(), dto.getTextDescription());
        assertEquals(productline.getHtmlDescription(), dto.getHtmlDescription());
        assertArrayEquals(productline.getImage(), dto.getImage());
    }

    @Test
    public void shouldUpdateProductline() {

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
                .save(any(Productline.class));

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
    public void shouldNotUpdateProductline_notExists() {

        doReturn(Optional.empty())
                .when(productlineRepository)
                .findById(anyString());

        assertThrows(
                ProductlineNotFoundException.class,
                () -> productlineService.updateProductline(dto.getProductLine(), dto)
        );
    }

    @Test
    public void shouldNotSaveProductline_alreadyExists() {

        doReturn(true)
                .when(productlineRepository)
                .existsById(anyString());

        assertThrows(
                ProductlineAlreadyExists.class,
                () -> productlineService.saveProductline(dto)
        );
    }

    @Test
    public void shouldFindProductline() {

        doReturn(Optional.of(ProductlineFactory.getProductline()))
                .when(productlineRepository)
                .findById(anyString());

        assertDoesNotThrow(
                () -> productlineService.findProductlineByProductLine(dto.getProductLine())
        );
    }

    @Test
    public void shouldNotFindProductline() {

        doReturn(Optional.empty())
                .when(productlineRepository)
                .findById(anyString());

        assertThrows(
                ProductlineNotFoundException.class,
                () -> productlineService.findProductlineByProductLine("productLine")
        );
    }

    @Test
    public void shouldFindImageTest() {

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
    public void shouldNotFindImageTest() {

        doThrow(ProductlineNotFoundException.class)
                .when(productlineRepository)
                .findById(anyString());

        assertThrows(
                ProductlineNotFoundException.class,
                () -> productlineService.getImageByProductLine("productLine")
        );
    }

}

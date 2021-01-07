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
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductlineServiceTests {

    @Autowired
    private ProductlineRepository productlineRepository;

    @Autowired
    private ProductlineService productlineService;

    private ProductlineDTO dto;

    @Before
    public void setUp() {
        dto = ProductlineFactory.getPoductlineDto();
        productlineRepository.deleteAll();
    }

    @Test
    public void shouldSaveProductline() {

        dto = productlineService.saveProductline(dto);

        assertNotNull(dto);
    }

    @Test
    public void shouldUpdateProductline() {

        final String newHTMLdescription = "new HTML description";

        dto = productlineService.saveProductline(dto);

        dto.setHtmlDescription(newHTMLdescription);

        dto = productlineService.updateProductline(dto);

        assertNotNull(dto);
        assertEquals(newHTMLdescription, dto.getHtmlDescription());
    }

    @Test
    public void shouldNotUpdateProductline_notExists() {

        assertThrows(
                ProductlineNotFoundException.class,
                () -> productlineService.updateProductline(dto)
        );
    }

    @Test
    public void shouldNotSaveProductline_alreadyExists() {

        dto = productlineService.saveProductline(dto);

        assertNotNull(dto);

        assertThrows(
                ProductlineAlreadyExists.class,
                () -> productlineService.saveProductline(dto)
        );
    }

    @Test
    public void shouldFindProductline() {

        dto = productlineService.saveProductline(dto);

        assertNotNull(dto);

        Productline productline = productlineService.findByProductline(dto.getProductLine());

        assertNotNull(productline);
    }

}

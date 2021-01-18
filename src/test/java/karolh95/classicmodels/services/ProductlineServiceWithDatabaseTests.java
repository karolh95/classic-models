package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.exceptions.ProductlineAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.utils.ProductlineFactory;
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
@Sql({"/drop_tables.sql", "/productlines.sql"})
@DisplayName("Productline service tests with database")
public class ProductlineServiceWithDatabaseTests {

    private static final int PRODUCTLINES_NUMBER = 7;
    private static final String PRODUCT_LINE = "Classic Cars";
    private static final String NON_EXISTING_PRODUCT_LINE = "Small Cars";

    @Autowired
    private ProductlineService productlineService;

    private static final Pageable PAGEABLE = PageRequest.of(0, 10);

    @Test
    public void findAllProductlineTest() {

        Page<ProductlineDTO> productlines = productlineService.findAllProductlines(PAGEABLE);

        assertEquals(PRODUCTLINES_NUMBER, productlines.getTotalElements());
    }

    @Test
    public void findProductlineByProductLineTest() {

        ProductlineDTO productlineDTO = productlineService.findProductlineByProductLine(PRODUCT_LINE);

        assertNotNull(productlineDTO);
        assertEquals(PRODUCT_LINE, productlineDTO.getProductLine());
    }

    @Test
    public void findProductlineByProductLine_productlineNotFoundTest() {

        assertThrows(
                ProductlineNotFoundException.class,
                () -> productlineService.findProductlineByProductLine(NON_EXISTING_PRODUCT_LINE)
        );
    }

    @Test
    public void saveProductlineTest() {

        ProductlineDTO productline = ProductlineFactory.getPoductlineDto();
        productline.setProductLine(NON_EXISTING_PRODUCT_LINE);

        assertThrows(
                ProductlineNotFoundException.class,
                () -> productlineService.findByProductline(NON_EXISTING_PRODUCT_LINE)
        );

        productline = productlineService.saveProductline(productline);

        assertNotNull(productline);
        assertEquals(NON_EXISTING_PRODUCT_LINE, productline.getProductLine());
    }

    @Test
    public void saveProductline_productlineAlreadyExistsTest() {

        ProductlineDTO productline = ProductlineFactory.getPoductlineDto();

        assertThrows(
                ProductlineAlreadyExistsException.class,
                () -> productlineService.saveProductline(productline)
        );
    }

    @Test
    public void updateProductlineTest() {

        String newDescription = "New Product line description";
        ProductlineDTO productlineDTO = productlineService.findProductlineByProductLine(PRODUCT_LINE);

        productlineDTO.setTextDescription(newDescription);

        productlineDTO = productlineService.updateProductline(PRODUCT_LINE, productlineDTO);

        assertNotNull(productlineDTO);
        assertEquals(PRODUCT_LINE, productlineDTO.getProductLine());
        assertEquals(newDescription, productlineDTO.getTextDescription());
    }

    @Test
    public void updateProductline_productlineNotFoundTest() {

        ProductlineDTO productline = ProductlineFactory.getPoductlineDto();
        productline.setProductLine(NON_EXISTING_PRODUCT_LINE);

        assertThrows(
                ProductlineNotFoundException.class,
                () -> productlineService.updateProductline(NON_EXISTING_PRODUCT_LINE, productline)
        );
    }

    @Test
    public void deleteProductlineTest() {

        productlineService.deleteProductline(PRODUCT_LINE);

        assertThrows(
                ProductlineNotFoundException.class,
                () -> productlineService.findByProductline(PRODUCT_LINE)
        );
    }
}

package karolh95.classicmodels.mappers;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductlineMapperTests {

    @Autowired
    private ProductlineMapper mapper;

    @Test
    public void shouldMapProductlineToDtoTest() {

        Productline productline = ProductlineFactory.getProductline();
        ProductlineDTO dto = mapper.productlineToDto(productline);

        assertNotNull(dto);
        assertEquals(productline.getProductLine(), dto.getProductLine());
        assertEquals(productline.getTextDescription(), dto.getTextDescription());
        assertEquals(productline.getHtmlDescription(), dto.getHtmlDescription());
        assertArrayEquals(productline.getImage(), dto.getImage());
    }

    @Test
    public void shouldMapDtoToProductlineTest() {

        ProductlineDTO dto = ProductlineFactory.getPoductlineDto();

        Productline productline = mapper.dtoToProductline(dto);

        assertNotNull(productline);
        assertEquals(dto.getProductLine(), productline.getProductLine());
        assertEquals(dto.getTextDescription(), productline.getTextDescription());
        assertEquals(dto.getHtmlDescription(), productline.getHtmlDescription());
        assertArrayEquals(dto.getImage(), productline.getImage());
    }
}

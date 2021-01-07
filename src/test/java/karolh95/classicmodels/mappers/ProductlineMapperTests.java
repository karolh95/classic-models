package karolh95.classicmodels.mappers;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.models.Productline;
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
    public void shouldMapProductlineToDto(){

        final String PRODUCTLINE = "Classic Cars";
        final String TEXT = "Attention car enthusiasts...";
        final String HTML = "<p>Attention car enthusiasts...</p>";
        final byte[] IMAGE = new byte[0];

        Productline productline = new Productline();
        productline.setProductLine(PRODUCTLINE);
        productline.setTextDescription(TEXT);
        productline.setHtmlDescription(HTML);
        productline.setImage(IMAGE);

        ProductlineDTO dto = mapper.productlineToDto(productline);

        assertNotNull(dto, "Dto should not be null");
        assertEquals(PRODUCTLINE, dto.getProductLine(), "ProductLine should match");
        assertEquals(TEXT, dto.getTextDescription(), "Text description should match");
        assertEquals(HTML, dto.getHtmlDescription(), "Html description should match");
        assertArrayEquals(IMAGE, dto.getImage(), "Image should match");
    }
}

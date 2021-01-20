package karolh95.classicmodels.mappers;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Productline Mapper Tests")
public class ProductlineMapperTests {

    private final ProductlineMapper mapper = Mappers.getMapper(ProductlineMapper.class);

    @Test
    public void mapProductlineToDtoTest() {

        Productline productline = ProductlineFactory.getProductline();
        ProductlineDTO dto = mapper.map(productline);

        assertNotNull(dto);
        assertEquals(productline.getProductLine(), dto.getProductLine());
        assertEquals(productline.getTextDescription(), dto.getTextDescription());
        assertEquals(productline.getHtmlDescription(), dto.getHtmlDescription());
        assertArrayEquals(productline.getImage(), dto.getImage());
    }

    @Test
    public void mapDtoToProductlineTest() {

        ProductlineDTO dto = ProductlineFactory.getPoductlineDto();

        Productline productline = mapper.map(dto);

        assertNotNull(productline);
        assertEquals(dto.getProductLine(), productline.getProductLine());
        assertEquals(dto.getTextDescription(), productline.getTextDescription());
        assertEquals(dto.getHtmlDescription(), productline.getHtmlDescription());
        assertArrayEquals(dto.getImage(), productline.getImage());
    }

    @Test
    public void updateProductlineFromDto() {

        final String NEW_PRODUCTLINE = "new productline";
        final String NEW_TEXT = "new text";
        final String NEW_HTML = "new HTML";
        final byte[] NEW_IMAGE = new byte[2];

        Productline productline = ProductlineFactory.getProductline();
        ProductlineDTO dto = mapper.map(productline);

        dto.setProductLine(NEW_PRODUCTLINE);
        dto.setTextDescription(NEW_TEXT);
        dto.setHtmlDescription(NEW_HTML);
        dto.setImage(NEW_IMAGE);

        mapper.update(dto, productline);

        assertNotEquals(NEW_PRODUCTLINE, productline.getProductLine());

        assertEquals(NEW_TEXT, productline.getTextDescription());
        assertEquals(NEW_HTML, productline.getHtmlDescription());
        assertArrayEquals(NEW_IMAGE, productline.getImage());
    }
}

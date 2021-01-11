package karolh95.classicmodels.mappers;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.models.Product;
import karolh95.classicmodels.utils.ProductFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTests {

    @Autowired
    private ProductMapper mapper;

    @Test
    public void mapProductToDtoTest() {

        Product product = ProductFactory.getProduct();

        ProductDto dto = mapper.map(product);

        assertNotNull(dto);
        assertEquals(product.getProductCode(), dto.getProductCode());
        assertEquals(product.getProductName(), dto.getProductName());
        assertEquals(product.getProductLine(), dto.getProductLine());
        assertEquals(product.getProductScale(), dto.getProductScale());
        assertEquals(product.getProductVendor(), dto.getProductVendor());
        assertEquals(product.getProductDescription(), dto.getProductDescription());
        assertEquals(product.getQuantityInStock(), dto.getQuantityInStock());
        assertEquals(product.getBuyPrice(), dto.getBuyPrice());
        assertEquals(product.getMSRP(), dto.getMSRP());
    }

    @Test
    public void mapDtoToProductTest() {

        ProductDto dto = ProductFactory.getProductDto();

        Product product = mapper.map(dto);

        assertNotNull(product);
        assertEquals(dto.getProductCode(), product.getProductCode());
        assertEquals(dto.getProductName(), product.getProductName());
        assertEquals(dto.getProductLine(), product.getProductLine());
        assertEquals(dto.getProductScale(), product.getProductScale());
        assertEquals(dto.getProductVendor(), product.getProductVendor());
        assertEquals(dto.getProductDescription(), product.getProductDescription());
        assertEquals(dto.getQuantityInStock(), product.getQuantityInStock());
        assertEquals(dto.getBuyPrice(), product.getBuyPrice());
        assertEquals(dto.getMSRP(), product.getMSRP());
    }

    @Test
    public void updateProductFromDtoTest() {

        Product product = ProductFactory.getProduct();
        ProductDto dto = ProductFactory.getProductDto();

        dto.setProductCode("new code");
        dto.setProductName("new name");
        dto.setProductLine("other line");
        dto.setProductScale("1:1");
        dto.setProductVendor("new vendor");
        dto.setProductDescription("new description");
        dto.setQuantityInStock(Short.parseShort("10"));
        dto.setBuyPrice(BigDecimal.valueOf(10));
        dto.setMSRP(BigDecimal.valueOf(200));

        mapper.update(dto, product);

        assertNotEquals(dto.getProductCode(), product.getProductCode());

        assertEquals(dto.getProductName(), product.getProductName());
        assertEquals(dto.getProductLine(), product.getProductLine());
        assertEquals(dto.getProductScale(), product.getProductScale());
        assertEquals(dto.getProductVendor(), product.getProductVendor());
        assertEquals(dto.getProductDescription(), product.getProductDescription());
        assertEquals(dto.getQuantityInStock(), product.getQuantityInStock());
        assertEquals(dto.getBuyPrice(), product.getBuyPrice());
        assertEquals(dto.getMSRP(), product.getMSRP());
    }
}

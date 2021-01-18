package karolh95.classicmodels.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.services.ProductService;
import karolh95.classicmodels.services.ProductlineService;
import karolh95.classicmodels.utils.ProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;

import static karolh95.classicmodels.utils.ResultMatcherAdapter.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DisplayName("Product Validation Tests")
public class ProductValidationTests {

    private static final String API = "/api/productlines/productLine/products";
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductlineService productlineService;

    private static List<Arguments> invalidProducts() {

        BiConsumer<ProductDto, String> productCode = ProductDto::setProductCode;
        BiConsumer<ProductDto, String> productName = ProductDto::setProductName;
        BiConsumer<ProductDto, String> productScale = ProductDto::setProductScale;
        BiConsumer<ProductDto, String> productVendor = ProductDto::setProductVendor;
        BiConsumer<ProductDto, String> productDescription = ProductDto::setProductDescription;
        BiConsumer<ProductDto, Short> quantityInStock = ProductDto::setQuantityInStock;
        BiConsumer<ProductDto, BigDecimal> buyPrice = ProductDto::setBuyPrice;
        BiConsumer<ProductDto, BigDecimal> MSRP = ProductDto::setMSRP;

        return List.of(
                Arguments.of(prepareDto(productCode, null), "productCode", "not-empty"),
                Arguments.of(prepareDto(productCode, ""), "productCode", "not-empty"),
                Arguments.of(prepareDto(productCode, "A".repeat(16)), "productCode", "length"),
                Arguments.of(prepareDto(productName, null), "productName", "not-empty"),
                Arguments.of(prepareDto(productName, ""), "productName", "not-empty"),
                Arguments.of(prepareDto(productName, "A".repeat(71)), "productName", "length"),
                Arguments.of(prepareDto(productScale, null), "productScale", "not-empty"),
                Arguments.of(prepareDto(productScale, ""), "productScale", "not-empty"),
                Arguments.of(prepareDto(productScale, "A".repeat(11)), "productScale", "length"),
                Arguments.of(prepareDto(productVendor, null), "productVendor", "not-empty"),
                Arguments.of(prepareDto(productVendor, ""), "productVendor", "not-empty"),
                Arguments.of(prepareDto(productVendor, "A".repeat(51)), "productVendor", "length"),
                Arguments.of(prepareDto(productDescription, null), "productDescription", "not-empty"),
                Arguments.of(prepareDto(productDescription, ""), "productDescription", "not-empty"),
                Arguments.of(prepareDto(quantityInStock, null), "quantityInStock", "not-null"),
                Arguments.of(prepareDto(buyPrice, null), "buyPrice", "not-null"),
                Arguments.of(prepareDto(buyPrice, BigDecimal.valueOf(12345678901.0)), "buyPrice", "digits"),
                Arguments.of(prepareDto(buyPrice, BigDecimal.valueOf(12.001)), "buyPrice", "digits"),
                Arguments.of(prepareDto(MSRP, null), "MSRP", "not-null"),
                Arguments.of(prepareDto(MSRP, BigDecimal.valueOf(12345678901.0)), "MSRP", "digits"),
                Arguments.of(prepareDto(MSRP, BigDecimal.valueOf(0.001)), "MSRP", "digits")
        );
    }

    private static <T> ProductDto prepareDto(BiConsumer<ProductDto, T> biConsumer, T value) {

        ProductDto productDto = ProductFactory.getProductDto();
        biConsumer.accept(productDto, value);
        return productDto;
    }

    @ParameterizedTest(name = "[{index}] validated field: {1}, expected error: {2}")
    @MethodSource("invalidProducts")
    public void saveProduct(ProductDto productDto, String field, String expectedError) throws Exception {

        perform(post(API), productDto, field, expectedError);
    }

    @ParameterizedTest(name = "[{index}] validated field: {1}, expected error: {2}")
    @MethodSource("invalidProducts")
    public void updateProduct(ProductDto productDto, String field, String expectedError) throws Exception {

        perform(put(API + "/productCode"), productDto, field, expectedError);
    }

    private void perform(MockHttpServletRequestBuilder request, ProductDto productDto, String field, String expectedError) throws Exception {

        String content = mapper.writeValueAsString(productDto);
        String message = String.format("{product.%s.%s}", field, expectedError);

        request.content(content)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(equalTo(field, message));
    }
}

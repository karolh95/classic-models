package karolh95.classicmodels.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.services.ProductlineService;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.util.List;
import java.util.function.BiConsumer;

import static karolh95.classicmodels.utils.ResultMatcherAdapter.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ProductlineValidationTests {

    private static final String API = "/" + ProductlineController.MAPPING;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductlineService productlineService;

    private static List<Arguments> invalidProductlines() {

        BiConsumer<ProductlineDTO, String> productLine = ProductlineDTO::setProductLine;
        BiConsumer<ProductlineDTO, String> textDescription = ProductlineDTO::setTextDescription;
        BiConsumer<ProductlineDTO, String> htmlDescription = ProductlineDTO::setHtmlDescription;

        return List.of(
                Arguments.of(prepareDto(productLine, null), "productLine", "not-empty"),
                Arguments.of(prepareDto(productLine, ""), "productLine", "not-empty"),
                Arguments.of(prepareDto(productLine, "A".repeat(51)), "productLine", "length"),
                Arguments.of(prepareDto(textDescription, null), "textDescription", "not-empty"),
                Arguments.of(prepareDto(textDescription, ""), "textDescription", "not-empty"),
                Arguments.of(prepareDto(textDescription, "A".repeat(4001)), "textDescription", "length"),
                Arguments.of(prepareDto(htmlDescription, null), "htmlDescription", "not-empty"),
                Arguments.of(prepareDto(htmlDescription, ""), "htmlDescription", "not-empty")
        );
    }

    private static ProductlineDTO prepareDto(BiConsumer<ProductlineDTO, String> biConsumer, String value) {
        ProductlineDTO dto = ProductlineFactory.getPoductlineDto();
        biConsumer.accept(dto, value);
        return dto;
    }

    @ParameterizedTest
    @MethodSource("invalidProductlines")
    public void saveProductline(ProductlineDTO dto, String field, String expectedError) throws Exception {

        String message = String.format("{productline.%s.%s}", field, expectedError);

        mvc.perform(saveProductline(dto))
                .andExpect(status().isBadRequest())
                .andExpect(equalTo(field, message));
    }

    @ParameterizedTest
    @MethodSource("invalidProductlines")
    public void updateProductline(ProductlineDTO dto, String field, String expectedError) throws Exception {

        String message = String.format("{productline.%s.%s}", field, expectedError);

        mvc.perform(updateProductline(dto))
                .andExpect(status().isBadRequest())
                .andExpect(equalTo(field, message));
    }

    private MockMultipartHttpServletRequestBuilder saveProductline(ProductlineDTO dto) throws Exception {

        String body = mapper.writeValueAsString(dto);
        MockMultipartFile productlineDto = new MockMultipartFile("productline", "", MediaType.APPLICATION_JSON_VALUE, body.getBytes());
        MockMultipartFile image = new MockMultipartFile("image", dto.getImage());

        return multipart(API)
                .file(productlineDto)
                .file(image);
    }

    private MockMultipartHttpServletRequestBuilder updateProductline(ProductlineDTO dto) throws Exception {

        String content = mapper.writeValueAsString(dto);
        MockMultipartFile productline = new MockMultipartFile("productline", "", MediaType.APPLICATION_JSON_VALUE, content.getBytes());
        MockMultipartFile image = new MockMultipartFile("image", dto.getImage());

        MockMultipartHttpServletRequestBuilder builder = multipart(API + "/productLine");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        return builder.file(productline)
                .file(image);
    }
}

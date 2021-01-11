package karolh95.classicmodels.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.services.ProductlineService;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import static karolh95.classicmodels.utils.ResultMatcherAdapter.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@RunWith(SpringRunner.class)
public class ProductlineValidationTests {

    private static final String API = "/" + ProductlineController.MAPPING;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductlineService productlineService;

    private ProductlineDTO productlineDTO;

    @Before
    public void setUp() {
        productlineDTO = ProductlineFactory.getPoductlineDto();
    }

    @Test
    public void saveProductline_nullProductLineTest() throws Exception {

        productlineDTO.setProductLine(null);

        saveProductline("productLine", "{productline.productLine.not-empty}");
    }

    @Test
    public void saveProductline_emptyProductLineTest() throws Exception {

        productlineDTO.setProductLine("");

        saveProductline("productLine", "{productline.productLine.not-empty}");
    }

    @Test
    public void saveProductline_invalidLengthProductLineTest() throws Exception {

        productlineDTO.setProductLine("A".repeat(51));

        saveProductline("productLine", "{productline.productLine.length}");
    }

    @Test
    public void saveProductline_nullTextDescriptionTest() throws Exception {
        productlineDTO.setTextDescription(null);

        saveProductline("textDescription", "{productline.textDescription.not-empty}");
    }

    @Test
    public void saveProductline_emptyTextDescriptionTest() throws Exception {

        productlineDTO.setTextDescription("");

        saveProductline("textDescription", "{productline.textDescription.not-empty}");
    }

    @Test
    public void saveProductline_invalidLengthTextDescriptionTest() throws Exception {

        productlineDTO.setTextDescription("A".repeat(4001));

        saveProductline("textDescription", "{productline.textDescription.length}");
    }

    @Test
    public void saveProductline_nullHtmlDescription() throws Exception {

        productlineDTO.setHtmlDescription(null);

        saveProductline("htmlDescription", "{productline.htmlDescription.not-empty}");
    }

    @Test
    public void saveProductline_emptyHtmlDescriptionTest() throws Exception {

        productlineDTO.setHtmlDescription("");

        saveProductline("htmlDescription", "{productline.htmlDescription.not-empty}");
    }

    @Test
    public void updateProductline_nullProductLineTest() throws Exception {

        productlineDTO.setProductLine(null);

        updateProductline("productLine", "{productline.productLine.not-empty}");
    }

    @Test
    public void updateProductline_emptyProductLineTest() throws Exception {

        productlineDTO.setProductLine("");

        updateProductline("productLine", "{productline.productLine.not-empty}");
    }

    @Test
    public void updateProductline_invalidLengthProductLineTest() throws Exception {

        productlineDTO.setProductLine("A".repeat(51));

        updateProductline("productLine", "{productline.productLine.length}");
    }

    @Test
    public void updateProductline_nullTextDescriptionTest() throws Exception {
        productlineDTO.setTextDescription(null);

        updateProductline("textDescription", "{productline.textDescription.not-empty}");
    }

    @Test
    public void updateProductline_emptyTextDescriptionTest() throws Exception {

        productlineDTO.setTextDescription("");

        updateProductline("textDescription", "{productline.textDescription.not-empty}");
    }

    @Test
    public void updateProductline_invalidLengthTextDescriptionTest() throws Exception {

        productlineDTO.setTextDescription("A".repeat(4001));

        updateProductline("textDescription", "{productline.textDescription.length}");
    }

    @Test
    public void updateProductline_nullHtmlDescription() throws Exception {

        productlineDTO.setHtmlDescription(null);

        updateProductline("htmlDescription", "{productline.htmlDescription.not-empty}");
    }

    @Test
    public void updateProductline_emptyHtmlDescriptionTest() throws Exception {

        productlineDTO.setHtmlDescription("");

        updateProductline("htmlDescription", "{productline.htmlDescription.not-empty}");
    }

    private void saveProductline(String field, String expectedErrorMessage) throws Exception {

        mvc.perform(saveProductline(productlineDTO))
                .andExpect(status().isBadRequest())
                .andExpect(equalTo(field, expectedErrorMessage));
    }

    private void updateProductline(String field, String expectedErrorMessage) throws Exception {

        mvc.perform(updateProductline(productlineDTO))
                .andExpect(status().isBadRequest())
                .andExpect(equalTo(field, expectedErrorMessage));
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

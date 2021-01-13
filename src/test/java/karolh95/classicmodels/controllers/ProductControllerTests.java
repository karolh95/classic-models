package karolh95.classicmodels.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.exceptions.ProductAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.services.ProductService;
import karolh95.classicmodels.utils.ProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static karolh95.classicmodels.utils.ResultMatcherAdapter.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@DisplayName("Product Controller Tests")
public class ProductControllerTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Nested
    @DisplayName("saveProduct")
    class SaveProductTests {

        private static final String API = "/api/productlines/productLine/products";

        private final ProductDto productDto = ProductFactory.getProductDto();

        @Test
        public void saveProductTest() throws Exception {

            doReturn(productDto)
                    .when(productService)
                    .saveProduct(anyString(), any(ProductDto.class));

            mvc.perform(saveProduct(productDto))
                    .andExpect(status().isOk())
                    .andExpect(equalTo("productCode", productDto.getProductCode()));
        }

        @Test
        public void saveProduct_productlineNotFoundTest() throws Exception {

            doThrow(ProductlineNotFoundException.class)
                    .when(productService)
                    .saveProduct(anyString(), any(ProductDto.class));

            mvc.perform(saveProduct(productDto))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason(ProductlineNotFoundException.MESSAGE));
        }

        @Test
        public void saveProduct_productAlreadyExistsTest() throws Exception {

            doThrow(ProductAlreadyExistsException.class)
                    .when(productService)
                    .saveProduct(anyString(), any(ProductDto.class));

            mvc.perform(saveProduct(productDto))
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason(ProductAlreadyExistsException.MESSAGE));
        }

        private RequestBuilder saveProduct(ProductDto productDto) throws JsonProcessingException {

            String json = mapper.writeValueAsString(productDto);

            return post(API)
                    .content(json.getBytes())
                    .contentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }
}

package karolh95.classicmodels.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.exceptions.ProductAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductNotFoundException;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.services.ProductService;
import karolh95.classicmodels.utils.ProductFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static karolh95.classicmodels.utils.ArgumentMatchersAdapter.anyProductDto;
import static karolh95.classicmodels.utils.ResultMatcherAdapter.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@DisplayName("Product Controller Tests")
public class ProductControllerTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getAllProductsTest() throws Exception {

        Pageable pageable = PageRequest.of(0, 10);
        ProductDto dto = ProductFactory.getProductDto();
        List<ProductDto> list = List.of(dto, dto, dto);

        doReturn(new PageImpl<>(list, pageable, list.size()))
                .when(productService)
                .findAllProducts(anyString(), any(Pageable.class));

        mvc.perform(get("/api/productlines/productLine/products"))
                .andExpect(status().isOk())
                .andExpect(equalTo("first", true))
                .andExpect(equalTo("last", true))
                .andExpect(equalTo("empty", false))
                .andExpect(equalTo("totalPages", 1))
                .andExpect(equalTo("totalElements", list.size()))
                .andExpect(equalTo("size", pageable.getPageSize()))
                .andExpect(equalTo("number", pageable.getPageNumber()));
    }

    @Nested
    @DisplayName("saveProduct")
    class SaveProductTests {

        private static final String API = "/api/productlines/productLine/products";

        private final ProductDto productDto = ProductFactory.getProductDto();

        @Test
        public void saveProductTest() throws Exception {

            doReturn(productDto)
                    .when(productService)
                    .saveProduct(anyString(), anyProductDto());

            mvc.perform(saveProduct(productDto))
                    .andExpect(status().isOk())
                    .andExpect(equalTo("productCode", productDto.getProductCode()));
        }

        @Test
        public void saveProduct_productlineNotFoundTest() throws Exception {

            doThrow(ProductlineNotFoundException.class)
                    .when(productService)
                    .saveProduct(anyString(), anyProductDto());

            mvc.perform(saveProduct(productDto))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason(ProductlineNotFoundException.MESSAGE));
        }

        @Test
        public void saveProduct_productAlreadyExistsTest() throws Exception {

            doThrow(ProductAlreadyExistsException.class)
                    .when(productService)
                    .saveProduct(anyString(), anyProductDto());

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

    @Nested
    @DisplayName("updateProduct")
    class UpdateProductTests {

        private static final String API = "/api/productlines/productLine/products/productCode";

        @Test
        public void updateProductTest() throws Exception {

            ProductDto productDto = ProductFactory.getProductDto();
            doReturn(productDto)
                    .when(productService)
                    .updateProduct(anyString(), anyProductDto());

            mvc.perform(updateProduct(productDto))
                    .andExpect(status().isOk());
        }

        @Test
        public void updateProductTest_productNotFoundTest() throws Exception {

            ProductDto productDto = ProductFactory.getProductDto();
            doThrow(ProductNotFoundException.class)
                    .when(productService)
                    .updateProduct(anyString(), anyProductDto());

            mvc.perform(updateProduct(productDto))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason(ProductNotFoundException.MESSAGE));
        }

        private RequestBuilder updateProduct(ProductDto productDto) throws JsonProcessingException {

            String content = mapper.writeValueAsString(productDto);
            return post(API)
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON);
        }
    }

    @Nested
    @DisplayName("findProduct")
    class FindProductTests {

        private static final String API = "/api/productlines/productLine/products/productCode";

        @Test
        public void findProductTest() throws Exception {

            doReturn(ProductFactory.getProductDto())
                    .when(productService)
                    .findProductByProductCode(anyString());

            mvc.perform(get(API))
                    .andExpect(status().isOk());
        }

        @Test
        public void findProduct_productNotFoundTest() throws Exception {

            doThrow(ProductNotFoundException.class)
                    .when(productService)
                    .findProductByProductCode(anyString());

            mvc.perform(get(API))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason(ProductNotFoundException.MESSAGE));
        }
    }

    @Nested
    @DisplayName("deleteProduct")
    class DeleteProduct {

        private static final String API = "/api/productlines/productLine/products/productCode";

        @Test
        public void deleteProductTest() throws Exception {

            doNothing()
                    .when(productService)
                    .deleteProduct(anyString());

            mvc.perform(delete(API))
                    .andExpect(status().isNoContent());
        }

        @Test
        public void deleteProduct_productNotFoundTest() throws Exception {

            doThrow(ProductNotFoundException.class)
                    .when(productService)
                    .deleteProduct(anyString());

            mvc.perform(delete(API))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason(ProductNotFoundException.MESSAGE));
        }
    }
}

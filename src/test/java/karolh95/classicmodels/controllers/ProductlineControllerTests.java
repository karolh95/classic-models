package karolh95.classicmodels.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.exceptions.ProductlineAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.services.ProductlineService;
import karolh95.classicmodels.utils.ProductlineFactory;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.util.List;

import static karolh95.classicmodels.utils.ResultMatcherAdapter.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductlineController.class)
@DisplayName("Productline Controller Tests")
public class ProductlineControllerTests {

    private static final String API = "/" + ProductlineController.MAPPING;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductlineService service;

    @Test
    public void getAllProductlinesTest() throws Exception {

        PageRequest PAGE_REQUEST = PageRequest.of(0, 10);

        ProductlineDTO dto = ProductlineFactory.getPoductlineDto();
        List<ProductlineDTO> list = List.of(dto, dto, dto);

        doReturn(new PageImpl<>(list, PAGE_REQUEST, list.size()))
                .when(service)
                .findAllProductlines(any(Pageable.class));

        mvc.perform(get(API))
                .andExpect(status().isOk())
                .andExpect(equalTo("first", true))
                .andExpect(equalTo("last", true))
                .andExpect(equalTo("empty", false))
                .andExpect(equalTo("totalPages", 1))
                .andExpect(equalTo("totalElements", list.size()))
                .andExpect(equalTo("size", PAGE_REQUEST.getPageSize()))
                .andExpect(equalTo("number", PAGE_REQUEST.getPageNumber()));
    }

    @Nested
    @DisplayName("getProductlineByProductLine")
    class GetProductlineByProductLineTests {

        @Test
        public void getProductlineByProductLineTest() throws Exception {

            ProductlineDTO dto = ProductlineFactory.getPoductlineDto();
            doReturn(dto).when(service)
                    .findProductlineByProductLine(anyString());

            mvc.perform(get(API + "/productline"))
                    .andExpect(status().isOk())
                    .andExpect(equalTo("productLine", dto.getProductLine()))
                    .andExpect(equalTo("textDescription", dto.getTextDescription()))
                    .andExpect(equalTo("htmlDescription", dto.getHtmlDescription()));
        }

        @Test
        public void getProductlineByProductLine_productlineNotFoundTest() throws Exception {

            doThrow(ProductlineNotFoundException.class).when(service)
                    .findProductlineByProductLine(anyString());

            mvc.perform(get(API + "/productline"))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason(ProductlineNotFoundException.MESSAGE));
        }
    }

    @Nested
    @DisplayName("saveProductline")
    class SaveProductlineTests {

        @Test
        public void saveProductlineTest() throws Exception {

            ProductlineDTO dto = ProductlineFactory.getPoductlineDto();

            doReturn(dto)
                    .when(service)
                    .saveProductline(any(ProductlineDTO.class));

            mvc.perform(saveProductline(dto))
                    .andExpect(status().isOk())
                    .andExpect(equalTo("productLine", dto.getProductLine()))
                    .andExpect(equalTo("textDescription", dto.getTextDescription()))
                    .andExpect(equalTo("htmlDescription", dto.getHtmlDescription()));
        }

        @Test
        public void saveProductline_alreadyExistsTest() throws Exception {

            ProductlineDTO dto = ProductlineFactory.getPoductlineDto();

            doThrow(ProductlineAlreadyExistsException.class)
                    .when(service)
                    .saveProductline(any(ProductlineDTO.class));

            mvc.perform(saveProductline(dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason(ProductlineAlreadyExistsException.MESSAGE));
        }

        private MockMultipartHttpServletRequestBuilder saveProductline(ProductlineDTO dto) throws Exception {

            String body = mapper.writeValueAsString(dto);
            MockMultipartFile productlineDto = new MockMultipartFile("productline", "", MediaType.APPLICATION_JSON_VALUE, body.getBytes());
            MockMultipartFile image = new MockMultipartFile("image", dto.getImage());

            return multipart(API)
                    .file(productlineDto)
                    .file(image);
        }
    }

    @Nested
    @DisplayName("getProductlineImage")
    class GetProductlineImageTests {

        @Test
        public void getProductlineImageTest() throws Exception {

            byte[] image = new byte[10];
            doReturn(image)
                    .when(service)
                    .getImageByProductLine(anyString());

            mvc.perform(get(API + "/productLine/image"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                    .andExpect(content().bytes(image));
        }

        @Test
        public void getProductlineImage_ProductlineNotFoundTest() throws Exception {

            doThrow(ProductlineNotFoundException.class)
                    .when(service)
                    .getImageByProductLine(anyString());

            mvc.perform(get(API + "/productLine/image"))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason(ProductlineNotFoundException.MESSAGE));
        }
    }

    @Nested
    @DisplayName("updateProductline")
    class UpdateProductlineTests {

        @Test
        public void updateProductlineTest() throws Exception {

            ProductlineDTO productlineDTO = ProductlineFactory.getPoductlineDto();
            doReturn(productlineDTO)
                    .when(service)
                    .updateProductline(anyString(), any(ProductlineDTO.class));

            mvc.perform(updateProductline(productlineDTO))
                    .andExpect(status().isOk());
        }

        @Test
        public void updateProductline_ProductlineNotFoundTest() throws Exception {

            ProductlineDTO productlineDTO = ProductlineFactory.getPoductlineDto();
            doThrow(ProductlineNotFoundException.class)
                    .when(service)
                    .updateProductline(anyString(), any(ProductlineDTO.class));

            mvc.perform(updateProductline(productlineDTO))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason(ProductlineNotFoundException.MESSAGE));
        }

        private MockMultipartHttpServletRequestBuilder updateProductline(ProductlineDTO dto) throws Exception {

            String content = mapper.writeValueAsString(dto);
            MockMultipartFile productline = new MockMultipartFile("productline", "", MediaType.APPLICATION_JSON_VALUE, content.getBytes());
            MockMultipartFile image = new MockMultipartFile("image", dto.getImage());

            MockMultipartHttpServletRequestBuilder builder = multipart(API + "/" + dto.getProductLine());
            builder.with(request -> {
                request.setMethod("PUT");
                return request;
            });

            return builder.file(productline)
                    .file(image);
        }
    }

    @Nested
    @DisplayName("deleteProductline")
    class DeleteProductlineTests {

        @Test
        public void deleteProductline() throws Exception {

            doNothing()
                    .when(service)
                    .deleteProductline(anyString());

            mvc.perform(delete(API + "/productLine"))
                    .andExpect(status().isNoContent());
        }

        @Test
        public void deleteProductline_productlineNotFoundTest() throws Exception {

            doThrow(ProductlineNotFoundException.class)
                    .when(service)
                    .deleteProductline(anyString());

            mvc.perform(delete(API + "/productLine"))
                    .andExpect(status().isNotFound())
                    .andExpect(status().reason(ProductlineNotFoundException.MESSAGE));
        }
    }
}
package karolh95.classicmodels.controllers;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.services.ProductlineService;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static karolh95.classicmodels.utils.ResultMatcherAdapter.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class ProductlineControllerTest {

    private static final String API = "/" + ProductlineController.MAPPING;
    private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 10);

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductlineService service;

    @Test
    public void getAllProductlinesTest() throws Exception {

        ProductlineDTO dto = ProductlineFactory.getPoductlineDto();
        List<ProductlineDTO> list = List.of(dto, dto, dto);

        doReturn(new PageImpl<>(list, PAGE_REQUEST, list.size()))
                .when(service)
                .findAllProductlines(anyInt(), anyInt());

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
}
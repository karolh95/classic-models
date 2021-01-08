package karolh95.classicmodels.controllers;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.services.ProductlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProductlineController.MAPPING)
@RequiredArgsConstructor
public class ProductlineController {

    public static final String MAPPING = "api/productlines";

    private final ProductlineService productlineService;

    @GetMapping
    public Page<ProductlineDTO> getAllProductLines(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return productlineService.findAllProductlines(page, size);
    }
}

package karolh95.classicmodels.controllers;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("productlines/{productLine}/products")
    public ProductDto saveProduct(@PathVariable String productLine, @RequestBody ProductDto product) {
        return productService.saveProduct(productLine, product);
    }

    @PostMapping("productlines/{productLine}/products/{productCode}")
    public ProductDto updateProduct(@PathVariable String productLine, @PathVariable String productCode, @RequestBody ProductDto productDto) {
        return productService.updateProduct(productCode, productDto);
    }
}

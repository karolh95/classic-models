package karolh95.classicmodels.controllers;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/productlines/{productLine}/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDto saveProduct(@PathVariable String productLine, @RequestBody ProductDto product) {
        return productService.saveProduct(productLine, product);
    }

    @PostMapping("{productCode}")
    public ProductDto updateProduct(@PathVariable String productLine, @PathVariable String productCode, @RequestBody ProductDto productDto) {
        return productService.updateProduct(productCode, productDto);
    }

    @GetMapping("{productCode}")
    public ProductDto getProduct(@PathVariable String productLine, @PathVariable String productCode) {
        return productService.findProductByProductCode(productCode);
    }

    @DeleteMapping("{productCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String productCode) {
        this.productService.deleteProduct(productCode);
    }
}

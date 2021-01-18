package karolh95.classicmodels.controllers;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/productlines/{productLine}/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDto saveProduct(@PathVariable String productLine, @Valid @RequestBody ProductDto product) {
        return productService.saveProduct(productLine, product);
    }

    @PutMapping("{productCode}")
    public ProductDto updateProduct(@PathVariable String productLine, @PathVariable String productCode, @Valid @RequestBody ProductDto productDto) {
        return productService.updateProduct(productCode, productDto);
    }

    @GetMapping
    public Page<ProductDto> getAllProducts(@PathVariable String productLine, Pageable pageable) {
        return productService.findAllProducts(productLine, pageable);
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

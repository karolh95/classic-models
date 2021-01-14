package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.exceptions.ProductAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductNotFoundException;
import karolh95.classicmodels.mappers.ProductMapper;
import karolh95.classicmodels.models.Product;
import karolh95.classicmodels.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductlineService productlineService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto saveProduct(String productLine, ProductDto productDto) {

        if (productRepository.existsById(productDto.getProductCode()))
            throw new ProductAlreadyExistsException();

        Product product = productMapper.map(productDto);
        productlineService.setProductline(productLine, product);
        product = productRepository.save(product);

        return productMapper.map(product);
    }

    public ProductDto updateProduct(String productCode, ProductDto productDto) {

        Product product = findByProductCode(productCode);
        productMapper.update(productDto, product);
        product = productRepository.save(product);

        return productMapper.map(product);
    }

    public ProductDto findProductByProductCode(String productCode) {
        Product product = findByProductCode(productCode);
        return productMapper.map(product);
    }

    public Page<ProductDto> findAllProducts(String productLine, Pageable pageable) {
        return productRepository.findAllByProductLine(productLine, pageable)
                .map(productMapper::map);
    }

    public void deleteProduct(String productCode) {

        Product product = findByProductCode(productCode);
        productRepository.delete(product);
    }

    private Product findByProductCode(String productCode) {
        return productRepository.findById(productCode)
                .orElseThrow(ProductNotFoundException::new);
    }
}

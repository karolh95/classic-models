package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.exceptions.ProductAlreadyExistsException;
import karolh95.classicmodels.mappers.ProductMapper;
import karolh95.classicmodels.models.Product;
import karolh95.classicmodels.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
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
}

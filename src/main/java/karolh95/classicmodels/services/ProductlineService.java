package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.exceptions.ProductlineAlreadyExistsException;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.mappers.ProductlineMapper;
import karolh95.classicmodels.models.Product;
import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.repositories.ProductlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductlineService {

    private final ProductlineRepository productlineRepository;
    private final ProductlineMapper productlineMapper;

    public ProductlineDTO saveProductline(ProductlineDTO dto) {

        if (productlineRepository.existsById(dto.getProductLine()))
            throw new ProductlineAlreadyExistsException();

        Productline productline = productlineMapper.map(dto);
        productline = productlineRepository.save(productline);

        return productlineMapper.map(productline);
    }

    public ProductlineDTO updateProductline(String id, ProductlineDTO dto) {

        Productline productline = findByProductline(id);
        productlineMapper.update(dto, productline);
        productline = productlineRepository.save(productline);

        return productlineMapper.map(productline);
    }

    public ProductlineDTO findProductlineByProductLine(String productLine) {
        Productline productline = findByProductline(productLine);
        return productlineMapper.map(productline);
    }

    public Page<ProductlineDTO> findAllProductlines(Pageable pageable) {
        return productlineRepository.findAll(pageable)
                .map(productlineMapper::map);
    }

    public byte[] getImageByProductLine(String productLine) {
        return findByProductline(productLine).getImage();
    }

    public void deleteProductline(String productLine) {
        Productline productline = findByProductline(productLine);
        productlineRepository.delete(productline);
    }

    public void setProductline(String productLine, Product product) {

        Productline productline = findByProductline(productLine);
        product.setProductline(productline);
    }

    private Productline findByProductline(String productline) {
        return this.productlineRepository.findById(productline)
                .orElseThrow(ProductlineNotFoundException::new);
    }
}

package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.exceptions.ProductlineAlreadyExists;
import karolh95.classicmodels.exceptions.ProductlineNotFoundException;
import karolh95.classicmodels.mappers.ProductlineMapper;
import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.repositories.ProductlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductlineService {

    private final ProductlineRepository productlineRepository;
    private final ProductlineMapper productlineMapper;

    public ProductlineDTO saveProductline(ProductlineDTO dto) {

        if (productlineRepository.existsById(dto.getProductLine()))
            throw new ProductlineAlreadyExists();

        Productline productline = productlineMapper.dtoToProductline(dto);
        productline = productlineRepository.save(productline);

        return productlineMapper.productlineToDto(productline);
    }

    public ProductlineDTO updateProductline(ProductlineDTO dto) {

        Productline productline = findByProductline(dto.getProductLine());
        productlineMapper.updateProductlineFromDto(dto, productline);
        productline = productlineRepository.save(productline);

        return productlineMapper.productlineToDto(productline);
    }

    public ProductlineDTO findProductlineByProductLine(String productLine) {
        Productline productline = findByProductline(productLine);
        return productlineMapper.productlineToDto(productline);
    }

    public Page<ProductlineDTO> findAllProductlines(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productlineRepository.findAll(pageable)
                .map(productlineMapper::productlineToDto);
    }

    public byte[] getImageByProductLine(String productLine){
        return findByProductline(productLine).getImage();
    }

    private Productline findByProductline(String productline) {
        return this.productlineRepository.findById(productline)
                .orElseThrow(ProductlineNotFoundException::new);
    }
}

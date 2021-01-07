package karolh95.classicmodels.services;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.exceptions.ProductlineAlreadyExists;
import karolh95.classicmodels.mappers.ProductlineMapper;
import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.repositories.ProductlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductlineService {

    private final ProductlineRepository productlineRepository;
    private final ProductlineMapper productlineMapper;

    public ProductlineDTO saveProductline(ProductlineDTO dto){

        if (productlineRepository.existsById(dto.getProductLine()))
            throw new ProductlineAlreadyExists();

        Productline productline = productlineMapper.dtoToProductline(dto);
        productline = productlineRepository.save(productline);

        return productlineMapper.productlineToDto(productline);
    }


}

package karolh95.classicmodels.mappers;

import karolh95.classicmodels.dto.ProductDto;
import karolh95.classicmodels.models.Product;
import karolh95.classicmodels.models.Product_;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductMapper {

    ProductDto map(Product product);

    Product map(ProductDto productDto);

    @Mapping(target = Product_.PRODUCT_CODE, ignore = true)
    void update(ProductDto dto, @MappingTarget Product product);
}

package karolh95.classicmodels.mappers;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.models.Productline_;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductlineMapper {

    ProductlineDTO productlineToDto(Productline productline);

    Productline dtoToProductline(ProductlineDTO productlineDTO);

    @Mapping(target = Productline_.PRODUCT_LINE, ignore = true)
    void updateProductlineFromDto(ProductlineDTO dto, @MappingTarget Productline productline);
}

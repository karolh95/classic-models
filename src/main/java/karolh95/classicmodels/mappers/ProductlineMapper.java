package karolh95.classicmodels.mappers;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.models.Productline;
import org.mapstruct.Mapper;

@Mapper
public interface ProductlineMapper {

    ProductlineDTO productlineToDto(Productline productline);
}

package be.dsystem.thepriceisright.mappers;

import be.dsystem.thepriceisright.dtos.ProductEntityDto;
import be.dsystem.thepriceisright.model.ProductEntity;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductEntityMapper {
    ProductEntity toEntity(ProductEntityDto productEntityDto);

    ProductEntityDto toDto(ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductEntity partialUpdate(ProductEntityDto productEntityDto, @MappingTarget ProductEntity productEntity);

    default Page<ProductEntityDto> toDtoPage(Page<ProductEntity> entityPage) {
        return entityPage.map(this::toDto);
    }

}
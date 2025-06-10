package be.dsystem.thepriceisright.mappers;

import be.dsystem.thepriceisright.dtos.ShopEntityDto;
import be.dsystem.thepriceisright.model.ShopEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShopEntityMapper {
    ShopEntity toEntity(ShopEntityDto shopEntityDto);

    ShopEntityDto toDto(ShopEntity shopEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ShopEntity partialUpdate(ShopEntityDto shopEntityDto, @MappingTarget ShopEntity shopEntity);
}
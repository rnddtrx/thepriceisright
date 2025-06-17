package be.dsystem.thepriceisright.mappers;

import be.dsystem.thepriceisright.dtos.PriceEntityDto;
import be.dsystem.thepriceisright.model.PriceEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceEntityMapper {
    PriceEntity toEntity(PriceEntityDto priceEntityDto);

    PriceEntityDto toDto(PriceEntity priceEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PriceEntity partialUpdate(PriceEntityDto priceEntityDto, @MappingTarget PriceEntity priceEntity);
}
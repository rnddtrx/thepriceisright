package be.dsystem.thepriceisright.mappers;

import be.dsystem.thepriceisright.dtos.UserEntityProfileDto;
import be.dsystem.thepriceisright.model.UserEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityProfileMapper {
    UserEntity toEntity(UserEntityProfileDto userEntityProfileDto);

    UserEntityProfileDto toDto(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserEntityProfileDto userEntityProfileDto, @MappingTarget UserEntity userEntity);
}
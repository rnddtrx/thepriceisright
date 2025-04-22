package be.dsystem.thepriceisright.mappers;

import be.dsystem.thepriceisright.dtos.RoleEntityDto;
import be.dsystem.thepriceisright.model.RoleEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleEntityMapper {
    RoleEntity toEntity(RoleEntityDto roleEntityDto);

    RoleEntityDto toDto(RoleEntity roleEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoleEntity partialUpdate(RoleEntityDto roleEntityDto, @MappingTarget RoleEntity roleEntity);

    List<RoleEntityDto> toDtoList(List<RoleEntity> roleEntities);

}
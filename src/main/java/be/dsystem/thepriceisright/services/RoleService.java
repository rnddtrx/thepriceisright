package be.dsystem.thepriceisright.services;

import be.dsystem.thepriceisright.dtos.RoleEntityDto;
import be.dsystem.thepriceisright.mappers.RoleEntityMapper;
import be.dsystem.thepriceisright.repositories.RoleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleEntityRepository roleEntityRepository;
    @Autowired
    RoleEntityMapper roleEntityMapper;

    public List<RoleEntityDto> getAllRoles(){
        return roleEntityMapper.toDtoList(roleEntityRepository.findAll());
    }

    public RoleEntityDto createRole(RoleEntityDto roleEntityDto) {
        var roleEntity = roleEntityMapper.toEntity(roleEntityDto);
        var savedRole = roleEntityRepository.save(roleEntity);
        return roleEntityMapper.toDto(savedRole);
    }
}

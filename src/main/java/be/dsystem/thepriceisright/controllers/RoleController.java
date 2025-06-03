package be.dsystem.thepriceisright.controllers;

import be.dsystem.thepriceisright.dtos.RoleEntityDto;
import be.dsystem.thepriceisright.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/roles")
public class RoleController
{
    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public RoleEntityDto createRole(@RequestBody RoleEntityDto roleEntityDto) {
        return roleService.createRole(roleEntityDto);
    }

    //@GetMapping
    //public RoleEntityDto getRoleByName(@Param("rolename") String roleName) {
    //    return roleService.getRoleByName(roleName);
    //}
}
package be.dsystem.thepriceisright.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link be.dsystem.thepriceisright.model.RoleEntity}
 */
@Value
public class RoleEntityDto implements Serializable {
    Integer roleId;
    String roleName;
    String description;
}
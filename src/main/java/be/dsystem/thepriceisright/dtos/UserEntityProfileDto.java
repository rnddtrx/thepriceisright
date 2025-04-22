package be.dsystem.thepriceisright.dtos;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link be.dsystem.thepriceisright.model.UserEntity}
 */
@Value
public class UserEntityProfileDto implements Serializable {
    int userId;
    String userName;
    String firstName;
    String lastName;
    String address;
    String postalCode;
    String city;
    String country;
    String phoneNumber;
    String email;
    String dateOfBirth;
    Set<RoleEntityDto1> roleEntities;

    /**
     * DTO for {@link be.dsystem.thepriceisright.model.RoleEntity}
     */
    @Value
    public static class RoleEntityDto1 implements Serializable {
        String roleName;
    }
}
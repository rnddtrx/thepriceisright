package be.dsystem.thepriceisright.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link be.dsystem.thepriceisright.model.UserEntity}
 */
@Value
public class UserEntityDto implements Serializable {
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
    String password;
    String profilePictureUrl;
}
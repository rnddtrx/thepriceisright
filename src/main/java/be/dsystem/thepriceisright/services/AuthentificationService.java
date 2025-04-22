package be.dsystem.thepriceisright.services;

import be.dsystem.thepriceisright.dtos.UserEntityDto;
import be.dsystem.thepriceisright.dtos.UserEntityProfileDto;
import be.dsystem.thepriceisright.mappers.UserEntityProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserEntityProfileMapper userEntityProfileMapper;

    // Method to authenticate user
    public UserEntityProfileDto authenticate(String username, String password) {
        // Retrieve user by username
        var user = this.userService.getUserEntityByUsername(username);

        // Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            // Convert user entity to DTO
            return userEntityProfileMapper.toDto(user);
        }
        return null;
    }
}

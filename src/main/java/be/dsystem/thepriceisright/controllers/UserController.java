package be.dsystem.thepriceisright.controllers;

import be.dsystem.thepriceisright.dtos.UserEntityDto;
import be.dsystem.thepriceisright.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    //crate a new user
    @PostMapping()
    public ResponseEntity<UserEntityDto> createUser(@RequestBody UserEntityDto userEntityDto) {
        UserEntityDto createdUser = userService.addUser(userEntityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

}

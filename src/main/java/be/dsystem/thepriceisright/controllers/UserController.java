package be.dsystem.thepriceisright.controllers;

import be.dsystem.thepriceisright.dtos.UserEntityDto;
import be.dsystem.thepriceisright.dtos.UserEntityProfileDto;
import be.dsystem.thepriceisright.model.UserEntity;
import be.dsystem.thepriceisright.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    //Create a new user
    @PostMapping()
    public ResponseEntity<UserEntityDto> createUser(@RequestBody UserEntityDto userEntityDto) {
        UserEntityDto createdUser = userService.addUser(userEntityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    //Add a role to a user
    @PostMapping("/{username}/role/{role}")
    public ResponseEntity<UserEntityDto> addRole(@PathVariable String username, @PathVariable String role) {
        userService.addRoleToUser(username, role);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    //Get a user by username
    @GetMapping("/{username}")
    public ResponseEntity<UserEntityProfileDto> getUserByUsername(@PathVariable String username) {
        Optional<UserEntityProfileDto> user = userService.getUserByUsername(username);
        //.map(...) applies a function only if the Optional is non-empty
        // ResponseEntity::ok method reference : user -> ResponseEntity.ok(user)
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<UserEntityProfileDto> getMyProlile() {
        Optional<UserEntityProfileDto> user = userService.getMyUser();
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/picture")
    public ResponseEntity<byte[]> getProfilePicture()  {
        Optional<byte[]> picture = userService.getProfilePicture();
        return picture
                .map(resource -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // adapte si PNG, etc.
                        .body(resource))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping("/upload-picture")
    public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file) {
        Optional<UserEntity> ue = userService.uploadProfilePicture(file);
        if (ue.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload profile picture.");
        }
        else {
            return ResponseEntity.ok("Profile picture uploaded successfully!");
        }
    }
}

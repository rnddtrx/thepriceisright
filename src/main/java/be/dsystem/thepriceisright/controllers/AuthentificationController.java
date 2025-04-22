package be.dsystem.thepriceisright.controllers;

import be.dsystem.thepriceisright.dtos.CredentialsDto;
import be.dsystem.thepriceisright.dtos.UserEntityProfileDto;
import be.dsystem.thepriceisright.services.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {
    @Autowired
    private AuthentificationService authentificationService;

    @PostMapping
    public ResponseEntity<UserEntityProfileDto> authenticate(@RequestBody CredentialsDto credentials) {
        UserEntityProfileDto user = authentificationService.authenticate(credentials.getUsername(), credentials.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

}

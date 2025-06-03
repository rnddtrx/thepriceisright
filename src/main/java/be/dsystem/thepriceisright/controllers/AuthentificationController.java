package be.dsystem.thepriceisright.controllers;

import be.dsystem.thepriceisright.config.security.AuthenticationException;
import be.dsystem.thepriceisright.config.security.JwtTokenRequest;
import be.dsystem.thepriceisright.config.security.JwtTokenResponseDto;
import be.dsystem.thepriceisright.config.security.JwtTokenUtil;
import be.dsystem.thepriceisright.config.security.userdetails.MyUserDetails;
import be.dsystem.thepriceisright.dtos.CredentialsDto;
import be.dsystem.thepriceisright.dtos.UserEntityProfileDto;
import be.dsystem.thepriceisright.services.AuthentificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {
    //@Autowired
    //private AuthentificationService authentificationService;

    //@PostMapping
    //public ResponseEntity<UserEntityProfileDto> authenticate(@RequestBody CredentialsDto credentials) {
    //    UserEntityProfileDto user = authentificationService.authenticate(credentials.getUsername(), credentials.getPassword());
    //    if (user != null) {
    //        return ResponseEntity.ok(user);
    //    } else {
    //        return ResponseEntity.status(401).build();
    //    }
    //}

    @Value("Authorization")
    private String tokenHeader;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    //Methode du controller pour recevoir un token (Adresse jwt.get.token.uri dans application properties)
    //@RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
            throws AuthenticationException {
        Authentication auth = this.authenticate(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword());

        final MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();

        String selectedRole = authenticationRequest.getSelectedRole();

        boolean roleIsValid = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals(selectedRole));

        if (!roleIsValid) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("The selected role is not allowed.");
        }

        final String token = jwtTokenUtil.generateToken(userDetails.getUsername(), selectedRole);

        return ResponseEntity.ok(new JwtTokenResponseDto(token));
    }

    //Methode du controller pour rafraichir un token (Adresse jwt.refresh.token.uri dans application properties)
    //@RequestMapping(value = "${jwt.refresh.token.uri}", method = RequestMethod.GET)
    @GetMapping()
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        MyUserDetails user = (MyUserDetails) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtTokenResponseDto(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //Exception
    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    //Methode d'authentification appelée dans createAuthenticationToken
    private Authentication authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }

}

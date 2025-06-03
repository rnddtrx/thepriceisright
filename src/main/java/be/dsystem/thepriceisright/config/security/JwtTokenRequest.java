package be.dsystem.thepriceisright.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class  JwtTokenRequest implements Serializable {
  private String username;
  private String password;
  private String selectedRole;
}


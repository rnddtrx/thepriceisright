package be.dsystem.thepriceisright.config.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtTokenResponseDto implements Serializable {
  private final String token;
}
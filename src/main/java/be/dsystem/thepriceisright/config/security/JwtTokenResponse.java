package be.dsystem.thepriceisright.config.security;

import java.io.Serializable;

public class JwtTokenResponse implements Serializable {


  private final String token;

    public JwtTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
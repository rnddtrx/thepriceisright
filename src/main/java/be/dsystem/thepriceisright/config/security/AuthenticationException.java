package be.dsystem.thepriceisright.config.security;

//Exception d'authentification
public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}


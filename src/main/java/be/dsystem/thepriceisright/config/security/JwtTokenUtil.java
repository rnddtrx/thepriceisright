package be.dsystem.thepriceisright.config.security;

import java.io.Serializable;
import java.security.Key;
import java.time.Clock;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import be.dsystem.thepriceisright.config.security.userdetails.MyUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;


@Component
public class JwtTokenUtil implements Serializable {

  static final String CLAIM_KEY_USERNAME = "sub";
  static final String CLAIM_KEY_CREATED = "iat";
  private Clock clock = Clock.systemUTC();

  //@Value("${jwt.signing.key.secret}")
  @Value("${jwt.secret}")
  private String base64Secret;


  @Value("${jwt.expiration}")
  private Long expiration;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getIssuedAtDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(Date.from(clock.instant()));
  }

  private Boolean ignoreTokenExpiration(String token) {
    // here you specify tokens, for that the expiration is ignored
    return false;
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList());
    return doGenerateToken(claims, userDetails.getUsername());
  }

  public String generateToken(String username, String selectedRole) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", selectedRole); // singular, not "roles"
    return doGenerateToken(claims, username);
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    final Date createdDate = Date.from(clock.instant());
    final Date expirationDate = calculateExpirationDate(createdDate);

    return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS512).
            compact();
  }

  public String getRoleFromToken(String token) {
    return getAllClaimsFromToken(token).get("role", String.class);
  }

  public Boolean canTokenBeRefreshed(String token) {
    return (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  public String refreshToken(String token) {
    final Date createdDate = Date.from(clock.instant());
    final Date expirationDate = calculateExpirationDate(createdDate);

    final Claims claims = getAllClaimsFromToken(token);
    claims.setIssuedAt(createdDate);
    claims.setExpiration(expirationDate);

    return Jwts.builder().setClaims(claims)
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    MyUserDetails user = (MyUserDetails) userDetails;
    final String username = getUsernameFromToken(token);
    return (username.equals(user.getUsername()) && !isTokenExpired(token));
  }

  private Date calculateExpirationDate(Date createdDate) {
    return new Date(createdDate.getTime() + expiration * 1000);
  }

  private Key getSigningKey() {
    byte[] keyBytes = Base64.getUrlDecoder().decode(base64Secret);
    return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());

  }
}

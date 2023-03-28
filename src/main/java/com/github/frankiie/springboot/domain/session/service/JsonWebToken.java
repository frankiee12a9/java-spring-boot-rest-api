package com.github.frankiie.springboot.domain.session.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.frankiie.springboot.domain.role.entity.Role;
import com.github.frankiie.springboot.domain.session.model.Authorized;
import com.github.frankiie.springboot.domain.user.entity.User;
import com.github.frankiie.springboot.domain.user.payload.UserResponse;

import static com.github.frankiie.springboot.constants.SECURITY.*;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.time.ZoneId.systemDefault;
import static java.util.Arrays.stream;
import static java.util.Date.from;

public class JsonWebToken {
  	private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebToken.class);

    public String encode(User user, LocalDateTime expiration, String secret) {
        var roles = user.getRoles().stream().map(Role::getAuthority).toList();
        return encode(user.getId(), roles, expiration, secret);
    }
    
    /**
     * factory encode JWT. This method helps extract and get the detailed information from current JWT 
     */
    public String encode(UserResponse user, LocalDateTime expiration, String secret) {
        return encode(user.getId(), user.getRoles(), expiration, secret);
    }

    public String encode(
        Long id,
        List<String> authorities,
        LocalDateTime expiration,
        String secret
    ) {
        return Jwts.builder()
            .setSubject(id.toString())
            .claim(ROLES_KEY_ON_JWT, String.join(",", authorities))
            .setExpiration(from(expiration.atZone(systemDefault()).toInstant()))
            .signWith(HS256, secret)
            .compact();
    }

    /**
     * factory encode JWT. This method helps extract and get the detailed information from current JWT 
     */
    public String encodeWithMoreDetails(UserResponse user, LocalDateTime expiration, String secret) {
        LOGGER.info("> encoded withMoreDetails: " + encode(user.getId(), user.getUsername(), user.getEmail(), user.getRoles(), expiration, secret));
        return encode(user.getId(), user.getUsername(), user.getEmail(), user.getRoles(), expiration, secret);
    }

    public String encode(
        Long id,
        String username,
        String email,
        List<String> authorities,
        LocalDateTime expiration,
        String secret
    ) {
        return Jwts.builder()
            .setSubject(id.toString())
            .claim("username", username)
            .claim("email", email)
            .claim(ROLES_KEY_ON_JWT, String.join(",", authorities))
            .setExpiration(from(expiration.atZone(systemDefault()).toInstant()))
            .signWith(HS256, secret)
            .compact();
    }

    /**
     * get CurrentUser ID from the JWT of current session
     */
    public Long getCurrentUserIdFromJWT(String token) {
      Claims claims = Jwts.parser()
          .setSigningKey(TOKEN_SECRET)
          .parseClaimsJws(token)
          .getBody();

		return Long.valueOf(claims.getSubject());
	}

    /**
     * decode and validate token 
     */
    public Authorized decode(String token, String secret) {
        var decoded = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

        var id = Long.parseLong(decoded.getBody().getSubject());
        
        var joinedRolesString = decoded.getBody().get(ROLES_KEY_ON_JWT).toString();
        var roles = joinedRolesString.split(",");
        var authorities = stream(roles).map(Role::new).toList();

        return new Authorized(id, authorities);
    }

    public boolean validateDecodedJWT(String token, String secret) {
      try {
        var ans = decode(token, token);
        return true;
      } catch (SignatureException ex) {
        LOGGER.error("Invalid JWT signature");
      } catch (MalformedJwtException ex) {
        LOGGER.error("Invalid JWT signature");
      } catch (ExpiredJwtException ex) {
        LOGGER.error("Expired JWT token");
      } catch (UnsupportedJwtException ex) {
        LOGGER.error("Unsupported JWT token");
      } catch (IllegalArgumentException ex) {
        LOGGER.error("JWT claims string is empty");
      }

      return false;
    }
}

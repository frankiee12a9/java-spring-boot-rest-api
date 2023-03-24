package com.github.frankiie.springboot.middlewares;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.frankiie.springboot.constants.SECURITY;
import com.github.frankiie.springboot.security.CustomUserDetailsServiceImpl;

// @Component
// @Order(2)
public class JWTAuthenticationMiddleware {
  
  //   @Autowired private SECURITY security;
  //   @Autowired private CustomUserDetailsServiceImpl customUserDetailsService;


  // 	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
  //     throws ServletException, IOException {
  //     try {
  //       String jwt = getJwtFromRequest(request);
  //       Long userId = this.security.JWT.getCurrentUserIdFromJWT(jwt);
  //       boolean validJwtCheck = StringUtils.hasText(jwt) && security.JWT.validateDecodedJWT(jwt, security.TOKEN_SECRET);
  //       if (validJwtCheck) {
  //         UserDetails userDetails = customUserDetailsService.loadUserById(userId);
  //         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
  //             userDetails.getAuthorities());
  //         authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

  //         SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  //       }
  //     } catch (Exception cause) {
  //       cause.printStackTrace();
  //     }

  //     filterChain.doFilter(request, response);
  //   }

  //   private String getJwtFromRequest(HttpServletRequest request) {
  //     String bearerToken = request.getHeader("Authorization");
  //     if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
  //       return bearerToken.substring(7, bearerToken.length());
  //     }
	// 	return null;
	// }
  
}

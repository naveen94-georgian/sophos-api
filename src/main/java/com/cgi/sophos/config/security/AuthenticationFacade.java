package com.cgi.sophos.config.security;

import com.cgi.sophos.exception.UnauthorizedException;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthenticationFacade {

  public String getAuthenticationToken() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal)
        .filter(f -> f instanceof Jwt)
        .map(Jwt.class::cast)
        .map(Jwt::getTokenValue)
        .orElseThrow(() -> new UnauthorizedException("error.token_not_found"));
  }
}

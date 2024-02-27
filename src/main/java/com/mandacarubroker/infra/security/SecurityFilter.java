package com.mandacarubroker.infra.security;

import com.mandacarubroker.domain.authuser.AuthUser;
import com.mandacarubroker.repository.AuthUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The method create a security filter for requests.
 **/
@Component
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final AuthUserRepository authUserRepository;

  public SecurityFilter(TokenService tokenService, AuthUserRepository authUserRepository) {
    this.tokenService = tokenService;
    this.authUserRepository = authUserRepository;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    var systemToken = this.recoverToken(request);

    if (systemToken != null) {
      var login = tokenService.validateToken(systemToken);
      AuthUser user = authUserRepository.findByLogin(login);

      var authentication = new UsernamePasswordAuthenticationToken(
          user,
          null,
          user.getAuthorities()
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");

    if (authHeader == null) {
      return null;
    }
    return authHeader.replace("Bearer ", "");
  }
}

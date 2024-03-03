package com.mandacarubroker.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mandacarubroker.controller.exceptions.StandardError;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



@AllArgsConstructor
@Component
public class TokenSecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository userRepository;
    private ObjectMapper objectMapper;
    private static final int CHARS_TO_SKIP = 7 ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if (token == null) {
            filterChain.doFilter(request,response);
            return;
        }
        try{
            String userSubject = tokenService.validateToken(token); //lançar exceção aqui
            User user = userRepository.findByUsername(userSubject).orElseThrow(()->new JWTVerificationException("user not found"));
            SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(user,null,user.getAuthorities()));
            filterChain.doFilter(request,response);
        }catch (JWTVerificationException exception){
            handleException(exception,request,response);
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header !=null ? header.substring(CHARS_TO_SKIP) : null; //"Bearer "
    }

    private void handleException(RuntimeException ex,HttpServletRequest req,HttpServletResponse res) throws IOException {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().print(objectMapper.writeValueAsString(new StandardError(Instant.now(), HttpStatus.UNAUTHORIZED.value(),
                "Invalid Token",ex.getMessage(),req.getRequestURI())));
    }
}

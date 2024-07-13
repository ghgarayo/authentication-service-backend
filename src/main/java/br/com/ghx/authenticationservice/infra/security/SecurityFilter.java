package br.com.ghx.authenticationservice.infra.security;

import br.com.ghx.authenticationservice.repository.UserRepository;
import br.com.ghx.authenticationservice.services.impl.TokenServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Inject
    private TokenServiceImpl tokenService;

    @Inject
    private UserRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = retrieveToken(request);

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            usuarioRepository.findByUsername(subject).map(usuario ->
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities())
            ).ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        }

        filterChain.doFilter(request, response);
    }

    private String retrieveToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }

        return null;
    }
}
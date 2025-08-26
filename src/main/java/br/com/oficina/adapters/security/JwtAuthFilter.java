package br.com.oficina.adapters.security;

import br.com.oficina.ports.AuthTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokens;

    public JwtAuthFilter(AuthTokenProvider tokens) {
        this.tokens = tokens;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Map<String, Object> claims = tokens.validateAndGetClaims(token);
                String subject = (String) claims.get("sub");

                // Authority simples baseada em roleId (poderia mapear para ROLE_ADMIN etc.)
                Object roleIdObj = claims.get("roleId");
                List<SimpleGrantedAuthority> auths = Collections.emptyList();
                if (roleIdObj != null) {
                    auths = List.of(new SimpleGrantedAuthority("ROLE_" + roleIdObj.toString()));
                }

                Authentication auth = new AbstractAuthenticationToken(auths) {
                    @Override public Object getCredentials() { return token; }
                    @Override public Object getPrincipal() { return subject; }
                };
                ((AbstractAuthenticationToken) auth).setAuthenticated(true);
                ((AbstractAuthenticationToken) auth).setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex) {
                // token inválido/expirado → segue sem auth; SecurityConfig vai barrar
            }
        }
        chain.doFilter(request, response);
    }
}

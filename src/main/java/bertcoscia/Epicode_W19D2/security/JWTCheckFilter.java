package bertcoscia.Epicode_W19D2.security;

import bertcoscia.Epicode_W19D2.entities.Dipendente;
import bertcoscia.Epicode_W19D2.exceptions.UnauthorizedException;
import bertcoscia.Epicode_W19D2.services.DipendentiService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckFilter extends OncePerRequestFilter {
    @Autowired
    JWTTools jwtTools;

    @Autowired
    DipendentiService dipendentiService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserisci correttamente il token nell'header Authorization");
        String accessToken = authHeader.substring(7);
        // Verifico il token
        jwtTools.verifyToken(accessToken);
        // Associo l'utente al token per iniziare il processo di autorizzazione
        // Cerco il dipendente tramite id
        String id = jwtTools.getIdFromToken(accessToken);
        Dipendente currentDipendente = this.dipendentiService.findById(UUID.fromString(id));
        // Associo il dipendente al security context
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentDipendente, null, currentDipendente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

    }

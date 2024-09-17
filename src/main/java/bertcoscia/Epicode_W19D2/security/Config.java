package bertcoscia.Epicode_W19D2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Config {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(AbstractHttpConfigurer::disable); // Rimuove form login
        httpSecurity.csrf(AbstractHttpConfigurer::disable); // Rimuove protezione CSFR
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Rimuove le sessioni
        httpSecurity.authorizeHttpRequests(http -> http.requestMatchers("/**").permitAll()); // Rimuove 401 da ogni richiesta
        /*
        FILTRI PERSONALIZZATI
         */
        return httpSecurity.build();
    }
}

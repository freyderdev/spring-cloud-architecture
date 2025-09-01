package com.freyder.springcloud.app.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.server.SecurityWebFilterChain;

// import reactor.core.publisher.Mono;
import org.springframework.lang.NonNull;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {
    // se implementa el método que mediante la cadena de filtros vamos a configurar
    // los permisos, los accesos de los roles a ciertos recursos o rutas
    @Bean
    SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(authz -> {
            authz.requestMatchers("/authorized", "/logout").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/items", "/api/products", "/api/users").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/items/{id}", "/api/products/{id}", "/api/users/{id}")
                    .hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/api/items/**", "/api/products/**", "/api/users/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
        }).cors(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login( login -> login.loginPage("/oauth2/authorization/client-app"))
                .oauth2Client(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                    //Aquí, se proporciona un conversor que tomará el JWT y extraerá la información de roles 
                    //para construir una instancia de autenticación
                        jwt -> jwt.jwtAuthenticationConverter(new Converter<Jwt, AbstractAuthenticationToken>() {

                            @Override
                            public AbstractAuthenticationToken convert(@NonNull Jwt source) {
                            //source.getClaimAsStringList("roles"):Extrae el claim "roles" del token JWT.
                             //Se espera que el JWT tenga un atributo llamado "roles", que sea una lista con los roles del user
                                Collection<String> roles = source.getClaimAsStringList("roles");
                            //Convierte los roles en una colección de GrantedAuthority
                            // Se mapea cada rol en un objeto SimpleGrantedAuthority, que es lo que Spring
                            // Security usa para autorizar accesos.si el token contiene roles: ["ADMIN", "USER"],
                            // esta colección se llenará con SimpleGrantedAuthority("ADMIN") y
                            // SimpleGrantedAuthority("USER")
                                Collection<GrantedAuthority> authorities = roles.stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList());
                                /* Se devuelve una instancia de JwtAuthenticationToken, que encapsula el JWT y
                                 * las autoridades extraídas.
                                 * Como la autenticación en WebFlux es reactiva, se usa Mono.just(...) para
                                 * devolver el resultado de manera asíncrona.
                                 */
                                return new JwtAuthenticationToken(source, authorities);
                            }
                        })))
                .build();
    }

}

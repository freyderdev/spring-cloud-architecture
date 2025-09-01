package com.freyder.springcloud.msvc.oauth.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.freyder.springcloud.msvc.oauth.models.User;

import io.micrometer.tracing.Tracer;

/*
 * La clase UserService se utiliza para cargar los detalles de un usuario desde un 
 * servicio remoto utilizando WebClient.  Convierte los roles del usuario a GrantedAuthority 
 * y retorna una instancia de UserDetails que Spring Security puede utilizar para la autenticación y autorización.
 */
@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    // private WebClient.Builder client;
    // For distributed tracing to work with the microservice, make sure Zipkin is properly configured.:
    private WebClient client; 
    
    @Autowired
    private Tracer tracer;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Ingresando al proceso de login UserService::loadUserByUsername:: con {}", username);

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        try {
            // User user = client.build().get().uri("/username/{username}", params)
            // For distributed tracing to work with the microservice, make sure Zipkin is properly configured.:
            User user = client.get().uri("/username/{username}", params)

                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class)// acá tenemos el usuario con sus roles
                    .block();

            // tenemos que convertir los roles a los roles de Spring Scurity es
            // GrantAuthority
            List<GrantedAuthority> roles = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            logger.info("Se ha  realizado el login con exito by username: {}", user);
            tracer.currentSpan().tag("success.login.message", "Se ha  realizado el login con exito by username: " + user);

            // tenemos que devolver un tipo UserDetails:
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    true,
                    true,
                    true,
                    roles);

        } catch (WebClientResponseException e) {
            String error = "Error en el login, no existe el users '" + username + "' en el sistema";
            logger.error(error);
            //vamos enviar mas informacion al zipkin con un tag:
            tracer.currentSpan().tag("error.login.message", error + ": " + e.getMessage());
            throw new UsernameNotFoundException(error);
        }

    }

}

package com.freyder.springcloud.app.gateway.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class SampleGlobalFilter implements Filter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);
    @Override
    public int getOrder() {
        return 100;

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
      logger.info("Llamada filtro SampleGlobalFilter:doFilter");         
      chain.doFilter(request, response); 
    }

}


// import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.core.Ordered;
// // import org.springframework.http.MediaType;
// import org.springframework.http.ResponseCookie;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;
// import org.springframework.http.server.reactive.ServerHttpRequest;

// import reactor.core.publisher.Mono;

// @Component
// public class SampleGlobalFilter implements GlobalFilter, Ordered {
//     // para poder mostrar como se ejecuta en consola:
//     private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

//         logger.info("Ejecutando el filtro antes del request PRE");

//         ServerHttpRequest mutatedRequest = exchange
//                                             .getRequest()
//                                             .mutate()
//                                             .headers(h -> h.add("token", "Violetta"))
//                                             .build();
//         ServerWebExchange mutatedExchange = exchange
//                                             .mutate()
//                                             .request(mutatedRequest)
//                                             .build();

//         // exchange.getRequest().mutate().headers(h -> h.add("token", "abcdefg"));

//         // el chain es para continuar con la ejecución de la cadena de los demas filtros
//         // hasta llegar a la ejecución del microservicio, de enviar el Request a nuestra
//         // API objetivo
//         return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//             logger.info("Ejecutando el filtro despues del request, osea POST response");
//             String token = mutatedExchange.getRequest().getHeaders().getFirst("token");
           
//             if(token !=null){
//                 logger.info("token: " + token);
//                 exchange.getResponse().getHeaders().add("token", token);
//             }
//             Optional.ofNullable(mutatedExchange.getRequest().getHeaders().getFirst("token"))
//             .ifPresent(value ->{
//                 logger.info("Token2: " + value);
//                 //pasamor el token a la cabecera de la respuesta:
//                 exchange.getResponse().getHeaders().add("token2", value);
//             });

//             //modificando la respuesta:
//             exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
//             // exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);

//         }));
//     }

//     @Override
//     public int getOrder() {
//         return 100; 

//     }
// }

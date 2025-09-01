package com.freyder.springcloud.app.gateway.filters.factory;


public class SampleCookieGatewayFilterFactory{
    
}



// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.cloud.gateway.filter.GatewayFilter;
// import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
// import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
// import org.springframework.http.ResponseCookie;
// import org.springframework.stereotype.Component;

// import reactor.core.publisher.Mono;

// @Component
// public class SampleCookieGatewayFilterFactory
//         extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigurationCookie> {

//     private final Logger logger = LoggerFactory.getLogger(SampleCookieGatewayFilterFactory.class);

//     // constructor:
//     public SampleCookieGatewayFilterFactory() {
//         super(ConfigurationCookie.class);
//     }

//     /*
//      * tenemos que implementar este método mediante una expresion Lambda:
//      * dentro del método GatewayFilter ya tenemos:
//      * Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain);
//      */
//     @Override
//     public GatewayFilter apply(ConfigurationCookie config) {

//         return new OrderedGatewayFilter((exchange, chain) -> {
//             // acá viene todo lo que viene ANTES en el "PRE" en el request
//             logger.info("Ejecutando pre gateway filter factory: " + config.message);

//             return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                
//                 Optional.ofNullable(config.value).ifPresent(cookie -> {
//                     exchange.getResponse()
//                             .addCookie(ResponseCookie.from(config.name, config.value).build());

//                 });

//                 // acá viene todo lo que viene DESPUES en el "POS" en el response:
//                 logger.info("Ejecutando post gateway filter factory: " + config.message);
//             }));
//         }, 100);
//     }
//     //método que sirve para darle un orden a los parametros de la Cookie 
//     //que esta configurada en el .application.yml
//     @Override
//     public List<String> shortcutFieldOrder() {
//         return Arrays.asList("message", "name", "value");
//     }

//     //metodo para cambiarle el nombre a la cookie configurada:
//     @Override
//     public String name() {
//         return "EjemploCookie";
//     }


//     // clase de configuracion interna:
//     public static class ConfigurationCookie {
//         private String name;
//         private String value;
//         private String message;

//         public String getName() {
//             return name;
//         }

//         public void setName(String name) {
//             this.name = name;
//         }

//         public String getValue() {
//             return value;
//         }

//         public void setValue(String value) {
//             this.value = value;
//         }

//         public String getMessage() {
//             return message;
//         }

//         public void setMessage(String message) {
//             this.message = message;
//         }
//     }

// }

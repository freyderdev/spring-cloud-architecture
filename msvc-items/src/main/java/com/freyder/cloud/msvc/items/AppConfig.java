package com.freyder.cloud.msvc.items;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {

    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> customizerCircuitBreaker() {

        return (factory) -> factory.configureDefault(id -> {
            // esta es la configuracion por defecto:
            // return new
            // Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build();
            return new Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig.custom()
                    .slidingWindowSize(10)// ventana deslizante
                    .failureRateThreshold(50)// humbral de errores
                    .waitDurationInOpenState(Duration.ofSeconds(10L))// tiempo de espera
                    .permittedNumberOfCallsInHalfOpenState(5)
                    /*
                     * slowCallDurationThreshold:
                     * establece el umbral de duración de una llamada lenta en 2 segundos. Esto
                     * significa que si una llamada tarda más de 2 segundos en completarse, se
                     * considerará una llamada lenta. 
                     */ 
                    .slowCallDurationThreshold(Duration.ofSeconds(2L))
                    // slowCallRateThreshold(50): If 50% or more of the calls are slow, it will be considered a failure.
                     .slowCallRateThreshold(50)  
                    .build())
                    .timeLimiterConfig(TimeLimiterConfig.custom()
                            .timeoutDuration(Duration.ofSeconds(3L)) // cuando ocurra 3 segundos va lanzar un timeOut
                            .build())

                    .build();
        });
    }
}

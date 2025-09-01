package com.freyder.springcloud.msvc.oauth;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    
    @Bean
    WebClient webClient(WebClient.Builder builder,
            ReactorLoadBalancerExchangeFilterFunction lFunction) {
        return builder
        .baseUrl("http://msvc-users")
        .filter(lFunction)
        .build();// esta será la ruta base
    }

    // @Bean
    // @LoadBalanced
    // WebClient.Builder webClient() {

    //     return WebClient.builder().baseUrl("http://msvc-users");//esta será la ruta base
    // }
    

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

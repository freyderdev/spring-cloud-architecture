package com.freyder.cloud.msvc.items;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    
    // metodo que va generar un componente de Spring del tipo webClient
    @Bean
    WebClient webClient(WebClient.Builder webClientBuilder,
            @Value("${config.baseurl.endpoint.msvc-products}") String url,
            ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        return webClientBuilder.baseUrl(url).filter(lbFunction).build();
    }
    // @Bean
    // @LoadBalanced
    // WebClient.Builder webClient(){
    //     return WebClient.builder().baseUrl(url);
    // }


}

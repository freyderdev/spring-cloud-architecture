package com.freyder.springcloud.app.gateway;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@SpringBootApplication
public class MsvcGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcGatewayServerApplication.class, args);
	}

	/*
	 * Este método define un bean que configura las rutas del gateway utilizando
	 * funciones de enrutamiento de Spring Cloud Gateway
	 */
	@Bean
	RouterFunction<ServerResponse> routerConfig(){

		return route("msvc-products")
			.route(path("/api/products/**"), http())
			// filtro personalizado:
			.filter((request, next) -> {
				/*
				 * 1️⃣Modificación de la Solicitud:
				 * Se crea una nueva solicitud (requestModified) a partir de la solicitud
				 * original (request).
				*/
				ServerRequest requestModified = ServerRequest.from(request)
						/*
						* Se agrega un encabezado (header) llamado "message-request" con el valor
						* "algun mensaje al request" a la solicitud modificada.
						*/
						.header("message-request", "algun mensaje al request").build();
				//2️⃣ Manejo de la solicitud modificada:
				ServerResponse response= next.handle(requestModified);
				response.headers().add("message-response", "algun mensaje para la respuesta");
				//finalmente devolevmos la respuesta:
				return response;
			})

		//filtro ya establecido
		.filter(lb("msvc-products"))
		.filter(circuitBreaker(config -> config
			.setId("products")
			.setStatusCodes("500")
			.setFallbackPath("forward:/api/items/5")))
		.before(stripPrefix(2)).build();

	}

}

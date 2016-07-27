package ca.tuatara.spring.cloud.zuul;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulCustomConfiguration {
	@Bean
	public RouteLocator dynamicRouteLocator(ServerProperties server, ZuulProperties properties) {
		return new DynamicRouteLocator(server.getServletPrefix(), properties);
	}
}

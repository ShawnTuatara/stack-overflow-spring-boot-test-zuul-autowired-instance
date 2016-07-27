package ca.tuatara.spring.cloud.zuul;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

public class DynamicRouteLocatorTest {
	@Test
	public void simpleRoute() {
		DynamicRouteLocator dynamicRouteLocator = buildDynamicRouteLocator();

		Route matchingRoute = dynamicRouteLocator.getMatchingRoute("/test/this");
		assertThat(matchingRoute.getLocation()).isEqualTo("http://localhost:8080");
	}

	@Test
	public void alterRoute() {
		DynamicRouteLocator dynamicRouteLocator = buildDynamicRouteLocator();

		ZuulProperties properties = dynamicRouteLocator.getProperties();
		properties.getRoutes().get("test-route").setLocation("http://localhost:8081");
		dynamicRouteLocator.setProperties(properties);
		dynamicRouteLocator.refresh();

		Route matchingRoute = dynamicRouteLocator.getMatchingRoute("/test/this");
		assertThat(matchingRoute.getLocation()).isEqualTo("http://localhost:8081");
	}

	private DynamicRouteLocator buildDynamicRouteLocator() {
		ZuulProperties properties = new ZuulProperties();
		Map<String, ZuulRoute> routes = new HashMap<String, ZuulProperties.ZuulRoute>();
		routes.put("test-route", new ZuulRoute("/test/**", "http://localhost:8080"));
		properties.setRoutes(routes);
		DynamicRouteLocator dynamicRouteLocator = new DynamicRouteLocator("/", properties);
		return dynamicRouteLocator;
	}
}

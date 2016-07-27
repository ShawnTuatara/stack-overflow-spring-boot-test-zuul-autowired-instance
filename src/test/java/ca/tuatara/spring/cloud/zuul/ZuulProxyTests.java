package ca.tuatara.spring.cloud.zuul;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class ZuulProxyTests {
	@Autowired
	private DynamicRouteLocator dynamicRouteLocator;

	@Rule
	public WireMockRule serviceA = new WireMockRule(Options.DYNAMIC_PORT);

	@Before
	public void before() {
		serviceA.stubFor(get(urlEqualTo("/test/path")).willReturn(aResponse().withStatus(200).withBody("test-body")));

		ZuulProperties properties = dynamicRouteLocator.getProperties();
		for (ZuulRoute route : properties.getRoutes().values()) {
			route.setLocation("http://localhost:" + serviceA.port());
		}
		dynamicRouteLocator.setProperties(properties);
		dynamicRouteLocator.refresh();
	}

	@After
	public void after() {
		ZuulProperties properties = dynamicRouteLocator.getProperties();
		for (ZuulRoute route : properties.getRoutes().values()) {
			route.setLocation("http://localhost:8080");
		}
		dynamicRouteLocator.setProperties(properties);
		dynamicRouteLocator.refresh();
	}
}

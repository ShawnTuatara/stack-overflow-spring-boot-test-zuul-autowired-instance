package ca.tuatara.spring.cloud.zuul;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

public class DynamicRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {
	private static Logger log = LoggerFactory.getLogger(DynamicRouteLocator.class);

	private static int id = 0;
	private int instanceId = id++;

	private ZuulProperties properties;

	public DynamicRouteLocator(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
		this.properties = properties;
	}

	public ZuulProperties getProperties() {
		return properties;
	}

	public void setProperties(ZuulProperties properties) {
		this.properties = properties;
	}

	@Override
	public void refresh() {
		log.info("Refreshing routes [{}]", this.instanceId);
		doRefresh();

		log.debug("{}", getRoutes());
	}

	@Override
	protected Map<String, ZuulRoute> locateRoutes() {
		LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
		for (ZuulRoute route : this.properties.getRoutes().values()) {
			routesMap.put(route.getPath(), route);
		}
		return routesMap;
	}

	@Override
	public Route getMatchingRoute(String path) {
		log.debug("Getting matching route [{}]: {}", this.instanceId, path);
		return super.getMatchingRoute(path);
	}
}

package ca.tuatara.spring.cloud.zuul;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ZuulProxyTests2 extends ZuulProxyTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testZuulProxy() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/test/path", String.class);
		assertThat(responseEntity.getBody()).isEqualTo("test-body");

		serviceA.verify(getRequestedFor(urlEqualTo("/test/path")));
	}
}

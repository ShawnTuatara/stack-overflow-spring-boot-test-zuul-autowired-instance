package ca.tuatara.spring.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class StackOverflowSpringBootTestZuulAutowiredInstanceApplication {
	public static void main(String[] args) {
		SpringApplication.run(StackOverflowSpringBootTestZuulAutowiredInstanceApplication.class, args);
	}
}

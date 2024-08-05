package fido.uz.mohir_dev_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class MohirDevJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MohirDevJpaApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofSeconds(60000)) // connection timeout
				.setReadTimeout(Duration.ofSeconds(60000))    // read timeout
				.build();
	}

}

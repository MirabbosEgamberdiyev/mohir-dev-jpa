package fido.uz.mohir_dev_jpa;

import fido.uz.mohir_dev_jpa.configuration.SwaggerUIOpener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;

@SpringBootApplication
public class MohirDevJpaApplication {


    @Value("${http.proxy.ip}")
    private String PROXY_SERVER_HOST;

    @Value("${http.proxy.port}")
    private Integer PROXY_SERVER_PORT;

    @Value("${http.use.proxy}")
    private Boolean useProxy;

    public static void main(String[] args) {
        SpringApplication.run(MohirDevJpaApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_SERVER_HOST, PROXY_SERVER_PORT));
            requestFactory.setProxy(proxy);
        }

        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(60))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();

        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }
    @Bean
    public CommandLineRunner openSwaggerUI(SwaggerUIOpener swaggerUIOpener) {
        return args -> swaggerUIOpener.openSwaggerUI();
    }

//  proxy siz holatda
//    public static void main(String[] args) {
//        SpringApplication.run(MohirDevJpaApplication.class, args);
//    }
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//
//        RestTemplate restTemplate = builder
//                .setConnectTimeout(Duration.ofSeconds(60))
//                .setReadTimeout(Duration.ofSeconds(60))
//                .build();
//
//        restTemplate.setRequestFactory(requestFactory);
//
//        return restTemplate;
//    }

}
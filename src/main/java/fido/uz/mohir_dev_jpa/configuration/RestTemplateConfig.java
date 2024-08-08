package fido.uz.mohir_dev_jpa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Value("${http.proxy.ip}")
    private String proxyServerHost;

    @Value("${http.proxy.port}")
    private Integer proxyServerPort;

    @Value("${http.use.proxy}")
    private Boolean useProxy;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServerHost, proxyServerPort));
            requestFactory.setProxy(proxy);
        }

        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(60))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();

        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }

//    Proxy siz foydalanish

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

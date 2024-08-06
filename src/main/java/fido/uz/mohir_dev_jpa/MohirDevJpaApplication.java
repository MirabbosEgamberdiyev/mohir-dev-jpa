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

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@SpringBootApplication
public class MohirDevJpaApplication {
//
//    @Value("${swagger.ui-url}")
//    private  String swaggerUiUrl;

    public static void main(String[] args) {
        SpringApplication.run(MohirDevJpaApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

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

//    @Bean
//    public CommandLineRunner openSwaggerUI() {
//        return args -> {
//            try {
//                URI uri = new URI(swaggerUiUrl);
//
//                // Detect the operating system
//                String os = System.getProperty("os.name").toLowerCase();
//
//                // Execute the command based on the operating system
//                if (os.contains("win")) {
//                    // Windows
//                    String command = "rundll32 url.dll,FileProtocolHandler " + uri.toString();
//                    Runtime.getRuntime().exec(command);
//                } else if (os.contains("mac")) {
//                    // macOS
//                    String command = "open " + uri.toString();
//                    Runtime.getRuntime().exec(command);
//                } else if (os.contains("nix") || os.contains("nux") || os.contains("nux")) {
//                    // Linux
//                    String command = "xdg-open " + uri.toString();
//                    Runtime.getRuntime().exec(command);
//                } else {
//                    throw new UnsupportedOperationException("Unsupported operating system: " + os);
//                }
//            } catch (IOException | URISyntaxException e) {
//                e.printStackTrace();
//            }
//        };
//    }

//    public static void openSwaggerUI() {
//        try {
//            String url = "http://localhost:9098/swagger-ui/index.html";
//            String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
//
//            ProcessBuilder processBuilder = new ProcessBuilder(chromePath, url);
//            processBuilder.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Value("${http.proxy.ip}")
//    private String PROXY_SERVER_HOST;
//
//    @Value("${http.proxy.port}")
//    private Integer PROXY_SERVER_PORT;
//
//    @Value("${http.use.proxy}")
//    private Boolean useProxy;
//
//    public static void main(String[] args) {
//        SpringApplication.run(MohirDevJpaApplication.class, args);
//    }
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        if (useProxy) {
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_SERVER_HOST, PROXY_SERVER_PORT));
//            requestFactory.setProxy(proxy);
//        }
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
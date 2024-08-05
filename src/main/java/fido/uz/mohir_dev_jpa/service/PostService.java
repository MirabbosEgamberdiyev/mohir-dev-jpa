package fido.uz.mohir_dev_jpa.service;

import fido.uz.mohir_dev_jpa.model.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PostService {
    private final RestTemplate restTemplate;

    @Value("${api.jsonplaceholder}")
    private String api;

    public PostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(
            value = { ResourceAccessException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public List<Post> findAll() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<List<Post>> entity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(this.api + "/posts", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Post>>() {}).getBody();
    }

    @Retryable(
            value = { ResourceAccessException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public Post findById(Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Post> entity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(this.api + "/posts/" + id, HttpMethod.GET, entity, Post.class).getBody();
    }
}

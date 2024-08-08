package fido.uz.mohir_dev_jpa.service;

import fido.uz.mohir_dev_jpa.dto.PostDto;
import fido.uz.mohir_dev_jpa.entity.PostData;
import fido.uz.mohir_dev_jpa.exception.CustomException;
import fido.uz.mohir_dev_jpa.model.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final RestTemplate restTemplate;
    private final PostDataService postDataService;

    @Value("${api.jsonplaceholder}")
    private String api;

    public PostService(RestTemplate restTemplate, PostDataService postDataService) {
        this.restTemplate = restTemplate;
        this.postDataService = postDataService;
    }

    @Retryable(
            value = { ResourceAccessException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public List<Post> findAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            List<Post> posts = restTemplate.exchange(api + "/posts", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Post>>() {})
                    .getBody();

            Optional.ofNullable(posts).ifPresent(postDataService::saveAll);
            return Optional.ofNullable(posts).orElse(Collections.emptyList());
        } catch (ResourceAccessException e) {
            throw new RuntimeException("Timeout error retrieving posts: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving posts: " + e.getMessage(), e);
        }
    }

    @Retryable(
            value = { ResourceAccessException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public Post findById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Post> response = restTemplate.exchange(api + "/posts/" + id, HttpMethod.GET, entity, Post.class);
            Post body = response.getBody();

            Optional.ofNullable(body).ifPresent(postDataService::save);
            return Optional.ofNullable(body).orElse(null);

        } catch (ResourceAccessException e) {
            throw new RuntimeException("Timeout error retrieving post: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving post: " + e.getMessage(), e);
        }
    }

    public Post save(PostDto post) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<PostDto> entity = new HttpEntity<>(post, headers);

        try {
            // Assuming `api` is correctly set to the base URL of the JSONPlaceholder API
            Post result = restTemplate.postForObject(this.api + "/posts", entity, Post.class);

            // If the API doesn't return these fields, manually set them from PostDto
            if (result != null) {
                result.setUserId(post.getUserId());
                result.setTitle(post.getTitle());
                result.setBody(post.getBody());
            }
            return result;
        } catch (Exception e) {
            try {
                throw new CustomException("Failed to create post", e);
            } catch (CustomException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public List<Post> findAllByQueryParam(){
        HttpEntity<List<Post>> entity = new HttpEntity<>(getHeader());
        List<Post> result = restTemplate.exchange(this.api + "/posts", HttpMethod.GET, entity, List.class).getBody();
        return  result;

    }

    private HttpHeaders getHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

}

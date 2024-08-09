package fido.uz.mohir_dev_jpa.controller;

import fido.uz.mohir_dev_jpa.dto.PostDto;
import fido.uz.mohir_dev_jpa.entity.PostData;
import fido.uz.mohir_dev_jpa.exception.ResponseMessage;
import fido.uz.mohir_dev_jpa.model.Post;
import fido.uz.mohir_dev_jpa.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Get a Post from jsonplaceholder",
            description = "Get a Post from jsonplaceholder.",
            tags = {"Post"}
    )
    @ApiResponse(responseCode = "200",
            description = "Post successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Post.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Posts not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @GetMapping("/posts")
    public ResponseEntity<?> getAll() {
        try {
            List<Post> posts = postService.findAll();
            return ResponseEntity.ok(posts);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(new ResponseMessage("Timeout error retrieving posts: " + e.getMessage(), 504), HttpStatus.GATEWAY_TIMEOUT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving posts: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get a specific Post by ID from jsonplaceholder",
            description = "Get a specific Post by ID from jsonplaceholder.",
            tags = {"Post"}
    )
    @ApiResponse(responseCode = "200",
            description = "Post successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Post.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Post not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Post post = postService.findById(id);
            if (post == null) {
                return new ResponseEntity<>(new ResponseMessage("Post not found", 404), HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(post);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(new ResponseMessage("Timeout error retrieving post: " + e.getMessage(), 504), HttpStatus.GATEWAY_TIMEOUT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error retrieving post: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(
            summary = "Create a Post from jsonplaceholder",
            description = "Create a Post from jsonplaceholder.",
            tags = {"Post"}
    )
    @ApiResponse(responseCode = "200",
            description = "Post created successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Post.class)
            )
    )
    @ApiResponse(responseCode = "400",
            description = "Bad Request",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @PostMapping("/posts")
    public ResponseEntity<?> create(@RequestBody PostDto post) {
        System.out.println("Received PostDto: " + post);
        try {
            Post result = postService.save(post);
            System.out.println("Saved Post: " + result);
            return ResponseEntity.ok(result);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(new ResponseMessage("Timeout error creating post: " + e.getMessage(), 504), HttpStatus.GATEWAY_TIMEOUT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error creating post: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get a Post from jsonplaceholder",
            description = "Get a Post from jsonplaceholder.",
            tags = {"Post"}
    )
    @ApiResponse(responseCode = "200",
            description = "Post successfully retrieved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Post.class)
            )
    )
    @ApiResponse(responseCode = "404",
            description = "Posts not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseMessage.class)
            )
    )
    @GetMapping("/posts/paging")
    public Page<PostData> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id,asc") String[] sort) {

        try {

            Pageable pageable = PageRequest.of(page, size, Sort.by(sort[0]));
            return postService.findAll(pageable);
        } catch (ResourceAccessException e) {
            return (Page<PostData>) new ResponseEntity<>(new ResponseMessage("Timeout error retrieving posts: " + e.getMessage(), 504), HttpStatus.GATEWAY_TIMEOUT);
        } catch (Exception e) {
            return (Page<PostData>) new ResponseEntity<>(new ResponseMessage("Error retrieving posts: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

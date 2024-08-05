package fido.uz.mohir_dev_jpa.service;

import fido.uz.mohir_dev_jpa.entity.PostData;
import fido.uz.mohir_dev_jpa.model.Post;
import fido.uz.mohir_dev_jpa.repository.PostDataRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostDataService {
    private final PostDataRepository postDataRepository;

    public PostDataService(PostDataRepository postDataRepository) {
        this.postDataRepository = postDataRepository;
    }

    public PostData save(PostData postData) {
        return postDataRepository.save(postData);
    }

    public List<PostData> saveAll(List<Post> posts) {
        if (posts == null) {
            return Collections.emptyList();
        }

        List<PostData> postDataList = posts.stream()
                .map(post -> {
                    PostData postData = new PostData();
                    postData.setPostId(post.getId());
                    postData.setUserId(post.getUserId());
                    postData.setTitle(post.getTitle());
                    postData.setBody(post.getBody());
                    return postData;
                })
                .collect(Collectors.toList());

        return postDataRepository.saveAll(postDataList);
    }
}

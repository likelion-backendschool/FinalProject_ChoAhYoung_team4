package com.ll.final_project.post;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void writePost(String subject, String content) {
        Post post = new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreatedDate(LocalDateTime.now());
        post.setUpdatedDate(LocalDateTime.now());
        postRepository.save(post);
    }
}

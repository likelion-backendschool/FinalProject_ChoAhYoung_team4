package com.ll.final_project.post;

import com.ll.final_project.post.PostDto.RequestPostDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void writePost(RequestPostDto requestPostDto) {
        Post post = new Post();
        post.setSubject(requestPostDto.getSubject());
        post.setContent(requestPostDto.getContent());
        post.setCreatedDate(LocalDateTime.now());
        post.setUpdatedDate(LocalDateTime.now());
        postRepository.save(post);
    }
}

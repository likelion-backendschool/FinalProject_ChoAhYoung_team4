package com.ll.final_project.post;

import com.ll.final_project.post.PostDto.RequestPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void writePost(RequestPostDto requestPostDto) {
        Post post = new Post(requestPostDto.getSubject(), requestPostDto.getContent());
        postRepository.save(post);
    }
}

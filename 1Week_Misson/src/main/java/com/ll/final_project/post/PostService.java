package com.ll.final_project.post;

import com.ll.final_project.base.DataNotFoundException;
import com.ll.final_project.post.PostDto.RequestPostDto;
import com.ll.final_project.post.PostDto.ResponsePostDto;
import java.time.LocalDateTime;
import java.util.List;
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

    public List<Post> getList() {
        return postRepository.findAll();
    }

    public ResponsePostDto getPost(Integer id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new DataNotFoundException("post not found");
        }
        return new ResponsePostDto(post);
    }

    public void deletePost(Integer id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new DataNotFoundException("post not found");
        }
        postRepository.delete(post);
    }

    public ResponsePostDto modifyPost(Integer id, RequestPostDto requestPostDto) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new DataNotFoundException("post not found");
        }

        post.setSubject(requestPostDto.getSubject());
        post.setContent(requestPostDto.getContent());
        post.setUpdatedDate(LocalDateTime.now());

       return new ResponsePostDto(postRepository.save(post));
    }
}

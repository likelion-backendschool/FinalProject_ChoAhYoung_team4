package com.ll.final_project.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping ("/list")
    @ResponseBody
    public String showPostList() {
        return "<h1>글 리스트</h1>";
    }

    @GetMapping ("/write")
    public String showWriteForm() {
        return "post_form";
    }

    @PostMapping ("/write")
    @ResponseBody
    public String writePost(@RequestParam String subject, @RequestParam String content) {
        postService.writePost(subject, content);
        return "글이 저장되었습니다.";
    }
}

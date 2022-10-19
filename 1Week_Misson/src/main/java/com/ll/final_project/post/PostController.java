package com.ll.final_project.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping ("/list")
    public String showPostList(Model model) {
        List<Post> postList = postService.getList();
        model.addAttribute("postList", postList);
        return "post_list";
    }

    @GetMapping ("/write")
    public String showWriteForm() {
        return "post_form";
    }

    @PostMapping ("/write")
    @ResponseBody
    public String writePost(PostDto.RequestPostDto requestPostDto) {
        postService.writePost(requestPostDto);
        return "글이 저장되었습니다.";
    }
}

package com.ll.final_project.post;

import com.ll.final_project.post.PostDto.ResponsePostDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 글 리스트 시작
    @GetMapping ("/list")
    public String showPostList(Model model) {
        List<Post> postList = postService.getList();
        model.addAttribute("postList", postList);
        return "/post/post_list";
    }
    // 글 리스트 종료

    // 글 작성 시작
    @GetMapping ("/write")
    public String showWriteForm() {
        return "post/post_form";
    }

    @PostMapping ("/write")
    public String writePost(PostDto.RequestPostDto requestPostDto) {
        postService.writePost(requestPostDto);
        return "redirect:/post/list";
    }
    // 글 작성 종료

    // 글 상세 시작
    @GetMapping("/{id}")
    public String showPostDetail(@PathVariable Integer id, Model model) {
        ResponsePostDto post = postService.getPost(id);

        model.addAttribute("post", post);
        return "/post/post_detail";
    }
    // 글 상세 종료

    // 글 삭제 시작
    @GetMapping("/{id}/delete")
    public String deletePost(@PathVariable Integer id) {
        postService.deletePost(id);

        return "redirect:/post/list";
    }
    // 글 삭제 종료

    // 글 수정 시작
    @GetMapping("/{id}/modify")
    public String showModifyPost(@PathVariable Integer id, Model model) {
        ResponsePostDto post = postService.getPost(id);
        model.addAttribute("post", post);
        return "/post/post_modify_form";
    }
    @PostMapping("{id}/modify")
    public String modifyPost(@PathVariable Integer id, PostDto.RequestPostDto requestPostDto, Model model) {
        ResponsePostDto post = postService.modifyPost(id, requestPostDto);
        model.addAttribute("post", post);
        return "/post/post_detail";
    }
    // 글 수정 종료
}

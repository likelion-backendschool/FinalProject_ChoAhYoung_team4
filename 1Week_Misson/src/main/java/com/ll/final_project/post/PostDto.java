package com.ll.final_project.post;

import lombok.Getter;
import lombok.Setter;

public class PostDto {

    @Getter
    @Setter
    public static class RequestPostDto { // Post 작성 폼을 받기위한 DTO
        private String subject;
        private String content;

        public RequestPostDto (String subject, String content) {
            this.subject = subject;
            this.content = content;
        }
    }
}

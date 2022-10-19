package com.ll.final_project.post;

import java.time.LocalDateTime;
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
    @Getter
    @Setter
    public static class ResponsePostDto { // Controller 로 Post 객체 정보를 전달하기 위한 DTO
        private Integer id;
        private String subject;
        private String content;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;

        public ResponsePostDto (Post post) {
            this.id = post.getId();
            this.subject = post.getSubject();
            this.content = post.getContent();
            this.createdDate = post.getCreatedDate();
            this.updatedDate = post.getUpdatedDate();
        }
    }
}

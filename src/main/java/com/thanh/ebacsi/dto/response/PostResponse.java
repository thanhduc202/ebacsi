package com.thanh.ebacsi.dto.response;

import com.thanh.ebacsi.entity.Feedback;
import com.thanh.ebacsi.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostResponse {
    private String status = "SUCCESS";
    private static final long serialVersionUID = 1L; // Thêm một serialVersionUID để đảm bảo tính nhất quán
    private Long postId;
    private Long userId;
    private Long categoryId;
    private String content;
    private String title;
    private Instant createAt;
    private Instant updateAt;
    private Set<Feedback> feedback;

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.content = post.getContent();
        this.title = post.getTitle();
        this.categoryId = post.getCategory().getCategoryId();
        this.createAt = post.getCreateAt();
        this.feedback = post.getFeedbacks();
        this.userId = post.getUsers().getUserId();
    }


}

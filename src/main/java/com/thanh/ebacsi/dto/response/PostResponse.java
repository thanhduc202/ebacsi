package com.thanh.ebacsi.dto.response;

import com.thanh.ebacsi.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostResponse {
    private String status = "SUCCESS";
    private Long postId;
    private Long userId;
    private Long categoryId;
    private String content;
    private String title;
    private Long createAt;
    private Long updateAt;

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.content = post.getContent();
        this.title = post.getTitle();
        this.categoryId = post.getCategory().getCategoryId();
        this.createAt = post.getCreateAt().toEpochMilli();
        this.updateAt = post.getUpdateAt().toEpochMilli();
        this.userId = post.getUsers().getUserId();
    }


}

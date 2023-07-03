package com.thanh.ebacsi.service.post;

import com.thanh.ebacsi.dto.request.PostRequest;
import com.thanh.ebacsi.dto.response.PostResponse;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    Post save(Post post);

    List<PostResponse> getAllPost();

    Post getPostsByPostId(Long postId);

    Post insertPost(PostRequest postRequest, String token);

    Post updatePost(PostRequest postRequest);

    List<PostResponse> findPostByCategoryId(Long categoryId);

    List<PostResponse> getPostByUsername(String username);

    List<PostResponse> search(String keyword);

    List<PostResponse> findPostByUserId(String token);

    Long getIdFromToken(String token);

    ResponseEntity<ResponseObject> deletePost(Long postId);

}

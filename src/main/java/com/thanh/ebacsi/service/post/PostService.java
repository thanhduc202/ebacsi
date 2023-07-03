package com.thanh.ebacsi.service.post;
import com.thanh.ebacsi.dto.request.PostRequest;
import com.thanh.ebacsi.dto.response.PostResponse;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    public Post save(Post post);

    List<PostResponse> getAllPost();

    public Post getPostsByPostId(Long postId);

    public Post insertPost(PostRequest postRequest, String token);

    public Post updatePost(PostRequest postRequest);

    public List<PostResponse> findPostByCategoryId(Long categoryId);

    public List<PostResponse> getPostByUsername(String username);

    public List<PostResponse> search (String keyword);

    List<PostResponse> findPostByUserId(String token);

    public Long getIdFromToken(String token);

    public ResponseEntity<ResponseObject> deletePost(Long postId);

}

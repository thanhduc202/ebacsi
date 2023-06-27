package com.thanh.ebacsi.service.post;
import com.thanh.ebacsi.dto.response.PostResponse;
import com.thanh.ebacsi.entity.Post;
import java.util.List;

public interface PostService {
    public Post save(Post post);

    List<PostResponse> getAllPost();

    public Post getPostsByPostId(Long postId);

    public Post insertPost(Post post);

    public List<PostResponse> findPostByCategoryId(Long categoryId);

    public List<PostResponse> getPostByUsername(String username);

    public List<PostResponse> search (String keyword);

    public Long getIdFromToken(String token);

}

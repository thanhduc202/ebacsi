package com.thanh.ebacsi.service.post;

import com.thanh.ebacsi.dto.response.PostResponse;
import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.entity.Post;
import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.CategoryRepository;
import com.thanh.ebacsi.repository.PostRepository;
import com.thanh.ebacsi.repository.UserRepository;

import com.thanh.ebacsi.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<PostResponse> getAllPost() {
        return postRepository.getAllPost();
    }

    @Override
    public Post getPostsByPostId(Long postId) {
        Post foundPost = postRepository.getPostsByPostId(postId);
        if(foundPost == null){
            throw new NotFoundException("Not found post by this id");
        }
        return foundPost;
    }

    @Override
    public Post insertPost(Post post) {
        List<Post> foundPost = postRepository.findByTitle(post.getTitle().trim());
        if (foundPost.size() > 0) {
            throw new NotFoundException("Post have existed");
        }
        return postRepository.save(post);
    }

    @Override
    public List<PostResponse> findPostByCategoryId(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(!category.isPresent()){
            throw new NotFoundException("Not found category");
        }
        List<PostResponse> listPost = postRepository.getPostsByCategoryId(categoryId);
        return listPost;
    }

    @Override
    public List<PostResponse> getPostByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new NotFoundException("not found user");
        }
        List<PostResponse> listPost = postRepository.getPostByUsername(username);
        return listPost;
    }

    @Override
    public List<PostResponse> search(String keyword) {
        if(keyword != null){
            return postRepository.search(keyword);
        }
        return postRepository.getAllPost();
    }

    @Override
    public Long getIdFromToken(String token) {
        String secret = "secret"; // Replace with your actual secret key
        // Remove the "Bearer " prefix from the token
        String jwtToken = token.replace("Bearer ", "");
        return jwtUtils.extractIdFromToken(secret, jwtToken);
    }
}

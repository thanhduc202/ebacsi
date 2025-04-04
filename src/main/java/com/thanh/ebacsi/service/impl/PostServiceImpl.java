package com.thanh.ebacsi.service.impl;

import com.thanh.ebacsi.dto.request.PostRequest;
import com.thanh.ebacsi.dto.response.PostResponse;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.entity.Post;
import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.CategoryRepository;
import com.thanh.ebacsi.repository.PostRepository;
import com.thanh.ebacsi.repository.UserRepository;
import com.thanh.ebacsi.security.Convert;
import com.thanh.ebacsi.security.JwtUtils;

import com.thanh.ebacsi.service.CategoryService;
import com.thanh.ebacsi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<PostResponse> getAllPost() {
        List<PostResponse> posts =  (List<PostResponse>) redisTemplate.opsForValue().get("post");
        if (posts == null) {
            posts = postRepository.getAllPost();
            redisTemplate.opsForValue().set("users", posts);

        }
        return posts;
    }

    @Override
    public PostResponse getPostsByPostId(Long postId) {
        Post foundPost = postRepository.getPostsByPostId(postId);
        if(foundPost == null){
            throw new NotFoundException("Not found post by this id");
        }
        PostResponse postResponse = new PostResponse(foundPost);
        return postResponse;
    }

    @Override
    public Post insertPost(PostRequest postRequest, String token) {
        List<Post> foundPost = postRepository.findByTitle(postRequest.getTitle().trim());
        if (foundPost.size() > 0) {
            throw new NotFoundException("Post have existed");
        }
        token = Convert.bearerTokenToToken(token);
        User user = userRepository.findByUsername(jwtUtils.extractUsername(token));
        Category category = categoryService.findById(postRequest.getCategoryId());
        Post post = new Post(postRequest);
        post.setCategory(category);
        post.setUsers(user);
        post.setCreateAt(new Date().toInstant());
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(PostRequest postRequest) {
        if(postRequest.getPostId() == null){
            throw new NotFoundException("Not found post");
        }
        Category category = categoryService.findById(postRequest.getCategoryId());
        Post post = postRepository.getPostsByPostId(postRequest.getPostId());
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCategory(category);
        post.setUpdateAt(new Date().toInstant());
        return postRepository.save(post);
    }

    @Override
    public List<PostResponse> findPostByCategoryId(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()){
            throw new NotFoundException("Not found category");
        }
        return postRepository.getPostsByCategoryId(categoryId);
    }

    @Override
    public List<PostResponse> getPostByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new NotFoundException("not found user");
        }
        return postRepository.getPostByUsername(username);
    }

    @Override
    public List<PostResponse> search(String keyword) {
        if(keyword != null){
            return postRepository.search(keyword);
        }
        return postRepository.getAllPost();
    }

    @Override
    public List<PostResponse> findPostByUserId(String token) {
        token = Convert.bearerTokenToToken(token);
        User user = userRepository.findByUsername(jwtUtils.extractUsername(token));
        Long userId = user.getUserId();
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public ResponseEntity<ResponseObject> deletePost(Long postId) {
        boolean exists = postRepository.existsById(postId);
        if(exists){
            postRepository.deleteById(postId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Delete successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Failed", "Post doesn't have existed", ""));
    }
}

package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.entity.Post;
import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.dto.request.PostRequest;
import com.thanh.ebacsi.dto.response.PostResponse;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.PostRepository;
import com.thanh.ebacsi.service.category.CategoryService;

import com.thanh.ebacsi.service.post.PostService;
import com.thanh.ebacsi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;


@EnableWebMvc
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/post")
@Slf4j
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/view")
    ResponseEntity<ResponseObject> getListPost() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Post query success", postService.getAllPost()));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findPostById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Post query success", postService.getPostsByPostId(id)));
    }

    @GetMapping("/category/{categoryId}")
    ResponseEntity<ResponseObject> getListPostByCategory_id(@PathVariable(name = "categoryId") Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Post query success", postService.findPostByCategoryId(categoryId)));
    }

    @GetMapping("/user")
    ResponseEntity<ResponseObject> getPostByUsername(@RequestBody UserInfoRequest userInfoRequest, String username) {
        username = userInfoRequest.getUsername();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Post query success", postService.getPostByUsername(username)));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> searchProducts(@RequestParam("keyword") String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Search success", postService.search(keyword)));
    }

    @PostMapping("/insert/{userId}")
    @PreAuthorize("hasAnyAuthority('EDITOR','AUTHOR','ADMIN')")
    ResponseEntity<PostResponse> insertPost(@RequestBody PostRequest postRequest, @PathVariable(name = "userId") Long userId) {
        User user = userService.findById(userId);
        Category category = categoryService.findById(postRequest.getCategoryId());
        // convert from postDTO to post entity object
        Post post = new Post(postRequest);
        //set user to post
        postRequest.setUserId(userId);
        post.setUsers(user);
        post.setCategory(category);
        post.setCreateAt(new Date().toInstant());
        //save post
        Post result = postRepository.save(post);
        return ResponseEntity.status(HttpStatus.OK).body(new PostResponse(postService.insertPost(result)));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','ADMIN')")
    ResponseEntity<PostResponse> updatePost(@RequestBody PostRequest postRequest) {
        if (postRequest.getPostId() == null) {
            throw new NotFoundException("Not found post");
        }
        Category category = categoryService.findById(postRequest.getCategoryId());
        Post post1 = postService.getPostsByPostId(postRequest.getPostId());
//        post1.setPostId(postRequest.getPostId());
        post1.setTitle(postRequest.getTitle());
        post1.setContent(postRequest.getContent());
        post1.setCategory(category);
        post1.setUpdateAt(new Date().toInstant());
        Post result = postRepository.save(post1);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new PostResponse(result));
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','ADMIN')")
    ResponseEntity<ResponseObject> deletePost(@PathVariable(name = "postId") Long postId) {
        boolean exists = postRepository.existsById(postId);
        postRepository.deleteById(postId);
        if (exists) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Delete successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Not Found post to delete", ""));
    }
}

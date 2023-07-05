package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.dto.request.PostRequest;
import com.thanh.ebacsi.dto.response.PostResponse;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/post")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping("/view")
    ResponseEntity<ResponseObject> getListPost() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Post query success", postService.getAllPost()));
    }
    @GetMapping("/view/user")
    ResponseEntity<ResponseObject> getPostByUserId(@RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Post query success", postService.findPostByUserId(token)));
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

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('EDITOR','AUTHOR','ADMIN')")
    ResponseEntity<PostResponse> insertPost(@RequestBody PostRequest postRequest, @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(new PostResponse(postService.insertPost(postRequest, token)));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','ADMIN')")
    ResponseEntity<PostResponse> updatePost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new PostResponse(postService.updatePost(postRequest)));
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','ADMIN')")
    ResponseEntity<ResponseObject> deletePost(@PathVariable(name = "postId") Long postId) {
        return postService.deletePost(postId);
    }
}

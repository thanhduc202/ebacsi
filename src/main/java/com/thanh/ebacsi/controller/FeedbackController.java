package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.dto.request.FeedbackRequest;

import com.thanh.ebacsi.dto.response.FeedbackResponse;

import com.thanh.ebacsi.dto.response.ResponseObject;

import com.thanh.ebacsi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/user/{userId}")
    ResponseEntity<ResponseObject> getFeedbackByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Feedback query success", feedbackService.getFeedbackByUserID(userId)));
    }

    @GetMapping("/post/{postId}")
    ResponseEntity<ResponseObject> getFeedbackByPostId(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Feedback query success", feedbackService.getFeedbackByPostID(postId)));
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('EDITOR','AUTHOR','ADMIN')")
    ResponseEntity<FeedbackResponse> insertPost(@RequestBody FeedbackRequest feedbackRequest, @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(new FeedbackResponse(feedbackService.insertFeedback(feedbackRequest, token)));
    }
}

package com.thanh.ebacsi.service.impl;

import com.thanh.ebacsi.dto.request.FeedbackRequest;
import com.thanh.ebacsi.dto.response.FeedbackResponse;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.entity.Category;
import com.thanh.ebacsi.entity.Feedback;
import com.thanh.ebacsi.entity.Post;
import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.FeedbackRepository;
import com.thanh.ebacsi.repository.PostRepository;
import com.thanh.ebacsi.repository.UserRepository;
import com.thanh.ebacsi.security.Convert;
import com.thanh.ebacsi.security.JwtUtils;
import com.thanh.ebacsi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PostRepository postRepository;


    @Override
    public List<Feedback> getFeedbackByPostID(Long postId) {
        List<Feedback> list = feedbackRepository.getFeedbackByPostID(postId);
        if(list == null){
            throw new NotFoundException("Not feedback by this postId");
        }
        return list;
    }

    @Override
    public List<FeedbackResponse> getFeedbackByUserID(Long userId) {
        List<FeedbackResponse> list = feedbackRepository.getFeedbackByUserID(userId);
        if(list == null){
            throw new NotFoundException("Not feedback by this postId");
        }
        return list;
    }

    @Override
    public Feedback insertFeedback(FeedbackRequest feedbackRequest, String token) {
        List<Feedback> foundFeedback = feedbackRepository.findByContent(feedbackRequest.getContent());
        if (foundFeedback.size() > 0) {
            throw new NotFoundException("feedback have existed");
        }
        token = Convert.bearerTokenToToken(token);
        User user = userRepository.findByUsername(jwtUtils.extractUsername(token));
        Post post = postRepository.getPostsByPostId(feedbackRequest.getPostId());

        Feedback fb = new Feedback();
        fb.setPost(post);
        fb.setUser(user);
        /*fb.setUsername(jwtUtils.extractUsername(token));*/
        fb.setContent(feedbackRequest.getContent());
        fb.setSubmitTime(new Date().toInstant());
        return feedbackRepository.save(fb);
    }

    @Override
    public ResponseEntity<FeedbackResponse> updateAllFeedback(FeedbackRequest feedbackRequest) {
        return null;
    }

    @Override
    public ResponseEntity<FeedbackResponse> updateOwnFeedback(FeedbackRequest feedbackRequest, String token) {
        token = Convert.bearerTokenToToken(token);
        User user = userRepository.findByUsername(jwtUtils.extractUsername(token));
        List<Feedback> foundFeedback = feedbackRepository.findFeedbackByUser(user.getUserId());
        if(foundFeedback.isEmpty()) {
            throw new NotFoundException("Not feedback by this user");
        }
        Feedback fb = null;
        Post post = postRepository.getPostsByPostId(feedbackRequest.getPostId());
        Set<Feedback> feedbacks = post.getFeedbacks();
        for (Feedback feedback : feedbacks) {
            Long feedbackId = feedback.getFeedbackId();
            if (feedbackId.equals(feedbackRequest.getFeedBackId())){
                feedback.setContent(feedbackRequest.getContent());
                fb = feedbackRepository.save(feedback);
                break;
            }
            if (fb == null) {
                throw new NotFoundException("Feedback not found for this post");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new FeedbackResponse(post.getPostId(),post.getUsers().getUserId(),
                fb.getContent(), post.getUsers().getUsername(),fb.getSubmitTime()));
    }


    @Override
    public ResponseEntity<ResponseObject> deleteFeedback(Long feedbackId) {
        return null;
    }
}

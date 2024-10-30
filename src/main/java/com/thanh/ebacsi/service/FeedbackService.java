package com.thanh.ebacsi.service;

import com.thanh.ebacsi.dto.request.FeedbackRequest;
import com.thanh.ebacsi.dto.response.FeedbackResponse;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.entity.Feedback;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FeedbackService {

    List<Feedback> getFeedbackByPostID(Long postId);
    List<FeedbackResponse> getFeedbackByUserID(Long userId);

    Feedback insertFeedback (FeedbackRequest feedbackRequest, String token);

    ResponseEntity<FeedbackResponse> updateAllFeedback (FeedbackRequest feedbackRequest);

    ResponseEntity<FeedbackResponse> updateOwnFeedback (FeedbackRequest feedbackRequest, String token);

    ResponseEntity<ResponseObject> deleteFeedback(Long feedbackId);


}

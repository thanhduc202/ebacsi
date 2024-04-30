package com.thanh.ebacsi.dto.response;

import com.thanh.ebacsi.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@Data
@NoArgsConstructor
public class FeedbackResponse {
    private Long postId;
    private Long userId;
    private Long feedbackId;
    private String content;
    private String username;
    private Long submitTime;

    public FeedbackResponse(Feedback feedback){
        this.postId = feedback.getPost().getPostId();
        this.userId = feedback.getUser().getUserId();
        this.feedbackId = feedback.getFeedbackId();
        this.content = feedback.getContent();
        this.submitTime = feedback.getSubmitTime().toEpochMilli();
    }
}

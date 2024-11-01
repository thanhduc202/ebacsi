package com.thanh.ebacsi.dto.response;

import com.thanh.ebacsi.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@AllArgsConstructor
@Data
public class FeedbackResponse {
    private Long postId;
    private Long userId;
    private String content;
    private String username;
    private Instant submitTime;

    public FeedbackResponse(Feedback feedback){
        this.postId = feedback.getPost().getPostId();
        this.userId = feedback.getUser().getUserId();
        this.content = feedback.getContent();
        this.submitTime = feedback.getSubmitTime();
    }

}

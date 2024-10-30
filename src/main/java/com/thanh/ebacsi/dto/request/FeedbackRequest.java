package com.thanh.ebacsi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class FeedbackRequest {
    private Long postId;
    private Long feedBackId;
    private Long userId;
    private String content;
}

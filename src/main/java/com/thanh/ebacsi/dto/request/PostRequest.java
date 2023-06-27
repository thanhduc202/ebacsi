package com.thanh.ebacsi.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
@AllArgsConstructor
public class PostRequest {
    private String content;
    private String title;
    private Long categoryId;
    private Long userId;
    private Long tagId;
}

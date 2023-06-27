package com.thanh.ebacsi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoRequest {
    private Long userId;
    private String username;
    private String password;
    private Boolean enable;

}

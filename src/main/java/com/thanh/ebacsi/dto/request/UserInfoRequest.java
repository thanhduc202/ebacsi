package com.thanh.ebacsi.dto.request;

import com.thanh.ebacsi.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoRequest {
    private String username;
    private String password;
    private Boolean enable;
//    private Long roleId;
}

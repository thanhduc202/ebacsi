package com.thanh.ebacsi.dto.response;

import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.entity.Role;
import com.thanh.ebacsi.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoResponse {
    private String status="SUCCESS";
    private Long userId;
    private String username;
    private Boolean enable;
    private Set<Role> role;

    public UserInfoResponse(User users) {
        this.userId = users.getUserId();
        this.username = users.getUsername();
        this.enable = users.getEnable();
        this.role = users.getRole();
    }
}

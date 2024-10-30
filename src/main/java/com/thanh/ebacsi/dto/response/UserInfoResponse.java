package com.thanh.ebacsi.dto.response;

import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.entity.Role;
import com.thanh.ebacsi.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoResponse implements Serializable {
    private String status="SUCCESS";
    private static final long serialVersionUID = 1L; // Thêm một serialVersionUID để đảm bảo tính nhất quán
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

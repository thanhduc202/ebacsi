package com.thanh.ebacsi.dto.response;

import com.thanh.ebacsi.entity.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
public class RoleResponse {
    private Long roleId;
    private String roleName;

    public RoleResponse(Role role){
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();

    }
}

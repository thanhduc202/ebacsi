package com.thanh.ebacsi.service.role;

import com.thanh.ebacsi.dto.response.RoleResponse;
import com.thanh.ebacsi.entity.Role;

import java.util.List;

public interface RoleService {

    public Role insertRole(Role role);

    public Role findByRoleId(Long roleId);

    public List<RoleResponse> getAllRole();

    public Role getRoleDefault();

    public Role getRoleByRoleId(Long roleId);
}

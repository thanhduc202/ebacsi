package com.thanh.ebacsi.service.role;

import com.thanh.ebacsi.dto.request.RoleRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.RoleResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RoleService {

    public Role findByRoleId(Long roleId);

    public List<RoleResponse> getAllRole();

    public Role getRoleDefault();

    public Role getRoleByRoleId(Long roleId);

    public List<RoleResponse> getRoleByUserId(Long userId);

    ResponseEntity<ResponseObject> getRoleByUser(String token);

    ResponseEntity<RoleResponse> insertRole(RoleRequest roleRequest);

    ResponseEntity<UserInfoResponse> insertRoleToUser(RoleRequest roleRequest);

    ResponseEntity<ResponseObject> deleteRole(Long roleId);

    ResponseEntity<UserInfoResponse> deleteRoleOfUser(RoleRequest roleRequest);
}

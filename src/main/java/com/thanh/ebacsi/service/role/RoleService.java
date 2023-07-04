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

    Role findByRoleId(Long roleId);

    List<RoleResponse> getAllRole();

    Role getRoleDefault();

    Role getRoleByRoleId(Long roleId);

    List<RoleResponse> getRoleByUserId(Long userId);

    ResponseEntity<ResponseObject> getRoleByUser(String token);

    ResponseEntity<RoleResponse> insertRole(RoleRequest roleRequest);

    ResponseEntity<UserInfoResponse> insertRoleToUser(RoleRequest roleRequest);

    ResponseEntity<ResponseObject> deleteRole(Long roleId);

    ResponseEntity<UserInfoResponse> deleteRoleOfUser(RoleRequest roleRequest);
}

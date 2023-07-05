package com.thanh.ebacsi.service;

import com.thanh.ebacsi.dto.request.RoleRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.RoleResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {

    List<RoleResponse> getAllRole();

    List<RoleResponse> getRoleByUserId(Long userId);

    ResponseEntity<ResponseObject> getRoleByUser(String token);

    ResponseEntity<RoleResponse> insertRole(RoleRequest roleRequest);

    ResponseEntity<UserInfoResponse> insertRoleToUser(RoleRequest roleRequest);

    ResponseEntity<ResponseObject> deleteRole(Long roleId);

    ResponseEntity<UserInfoResponse> deleteRoleOfUser(RoleRequest roleRequest);
}

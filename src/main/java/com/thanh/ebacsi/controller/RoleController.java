package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.dto.request.RoleRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.RoleResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @GetMapping("/view")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> getAllRole() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Role query success", roleService.getAllRole()));
    }

    @GetMapping("/view/user")
    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR','EDITOR')")
    ResponseEntity<ResponseObject> getRoleByUser(@RequestHeader("Authorization") String token) {
        return roleService.getRoleByUser(token);
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<RoleResponse> insertRole(@RequestBody RoleRequest roleRequest) {
        return roleService.insertRole(roleRequest);
    }

    @PostMapping("/insertRoleToUser")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<UserInfoResponse> insertRoleToUser(@RequestBody RoleRequest roleRequest) {
        return roleService.insertRoleToUser(roleRequest);
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> deleteRole(@PathVariable(name = "roleId") Long roleId) {
        return roleService.deleteRole(roleId);
    }

    @DeleteMapping("delete")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<UserInfoResponse> deleteRoleOfUser(@RequestBody RoleRequest roleRequest) {
        return roleService.deleteRoleOfUser(roleRequest);
    }
}

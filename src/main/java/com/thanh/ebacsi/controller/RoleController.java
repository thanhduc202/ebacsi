package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.dto.request.RoleRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.RoleResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.entity.Role;
import com.thanh.ebacsi.entity.User;

import com.thanh.ebacsi.repository.RoleRepository;
import com.thanh.ebacsi.repository.UserRepository;
import com.thanh.ebacsi.service.role.RoleService;

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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/view")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> getAllRole() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Role query success", roleService.getAllRole()));
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<RoleResponse> insertRole(@RequestBody RoleRequest roleRequest) {
        Role role = new Role(roleRequest);
        role.setRoleName(roleRequest.getRoleName());
        Role result = roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.OK).body(new RoleResponse(roleService.insertRole(result)));
    }

    @PostMapping("/insertRoleToUser")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<UserInfoResponse> insertRoleToUser(@RequestBody RoleRequest roleRequest) {
        User user = userRepository.findByUserId(roleRequest.getUserId());
        Role role = roleRepository.getRoleByRoleId(roleRequest.getRoleId());
        user.getRole().add(role);
        User result1 = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse((result1)));
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> deletePost(@PathVariable(name = "roleId") Long roleId) {
        boolean exists = roleRepository.existsById(roleId);
        roleRepository.deleteById(roleId);
        if (exists) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Delete successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Not Found role to delete", ""));
    }
}

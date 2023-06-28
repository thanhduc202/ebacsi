package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.dto.request.RoleRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.RoleResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.entity.Role;
import com.thanh.ebacsi.entity.User;

import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.RoleRepository;
import com.thanh.ebacsi.repository.UserRepository;
import com.thanh.ebacsi.security.Convert;
import com.thanh.ebacsi.security.JwtUtils;
import com.thanh.ebacsi.service.role.RoleService;

import com.thanh.ebacsi.service.user.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;
    @GetMapping("/view")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> getAllRole() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Role query success", roleService.getAllRole()));
    }

    @GetMapping("/view/user")
    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR','EDITOR')")
    ResponseEntity<ResponseObject> getRoleByUser(@RequestHeader("Authorization") String token) {
        token = Convert.bearerTokenToToken(token);
        User user = userService.findByUserName(jwtUtils.extractUsername(token));
        Long userId = user.getUserId();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Role query success", roleService.getRoleByUserId(userId)));
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
    ResponseEntity<ResponseObject> deleteRole(@PathVariable(name = "roleId") Long roleId) {
        boolean exists = roleRepository.existsById(roleId);
        roleRepository.deleteById(roleId);
        if (exists) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Delete successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Not Found role to delete", ""));
    }

    @DeleteMapping("delete")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<UserInfoResponse> deleteRoleOfUser(@RequestBody RoleRequest roleRequest) {
        Role role = roleRepository.getRoleByRoleId(roleRequest.getRoleId());
        User user = userRepository.findByUserId(roleRequest.getUserId());
        user.getRole().remove(role);
        if (role != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse(userRepository.save(user)));
        }
        throw new NotFoundException("Not found role of User");
    }
}

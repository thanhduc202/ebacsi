package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.entity.Role;
import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.repository.UserRepository;
import com.thanh.ebacsi.dto.response.TokenResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.security.Convert;
import com.thanh.ebacsi.security.JwtUtils;
import com.thanh.ebacsi.service.role.RoleService;
import com.thanh.ebacsi.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/view")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "User query success", userService.findAll()));
    }

    @GetMapping("/viewUserEnable")
    ResponseEntity<ResponseObject> getAllUserEnable() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "User query success", userService.getUserEnable()));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "User query success", userService.findById(id)));
    }

    @GetMapping("/search")
    ResponseEntity<UserInfoResponse> findByUsername(@RequestBody UserInfoRequest userInfoRequest) {
        User user = userService.findByUserName(userInfoRequest.getUsername());
        if (user == null) {
            throw new NotFoundException("Not found user");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse(userService.findByUserName(userInfoRequest.getUsername())));
    }

    @PostMapping("/auth/login")
    ResponseEntity<TokenResponse> authenticate(@RequestBody UserInfoRequest userInfoRequest) {
        return userService.login(userInfoRequest);
    }

    @PostMapping("/auth/register")
    ResponseEntity<UserInfoResponse> insertUser(@RequestBody UserInfoRequest userInfoRequest) {
        User foundUser = userRepository.findByUsername(userInfoRequest.getUsername().trim());
        //Register user auto save role is USER
        Role foundRole = roleService.getRoleDefault();
        if(foundRole == null){
            throw new NotFoundException("Not found role name");
        }
        User user = new User();
        user.setUsername(userInfoRequest.getUsername());
        user.setPassword(userInfoRequest.getPassword());
        user.setEnable(userInfoRequest.getEnable());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(foundRole);
        user.setRole(roleSet);
        User result = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse(result));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR')")
    ResponseEntity<UserInfoResponse> updateUser(@RequestBody UserInfoRequest userInfoRequest) {
        if (userInfoRequest.getUserId() == null) {
            throw new NotFoundException("Not found user");
        }
        User user = userService.findById(userInfoRequest.getUserId());
        user.setUsername(userInfoRequest.getUsername());
        user.setPassword(userInfoRequest.getPassword());
        User result = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new UserInfoResponse(result));
    }
    @PutMapping("/changePass")
    ResponseEntity<UserInfoResponse> changePassword(@RequestBody UserInfoRequest userInfoRequest, @RequestHeader("Authorization") String token){
        token = Convert.bearerTokenToToken(token);
        User user = userService.findByUserName(jwtUtils.extractUsername(token));
        user.setPassword(userInfoRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse(userRepository.save(user)));
    }
    @PutMapping("/disable")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> disableUser(@RequestBody UserInfoRequest userInfoRequest) {
        User user = userService.findById(userInfoRequest.getUserId());
        user.setEnable(false);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "user query success",userRepository.save(user)));
    }

    @PutMapping("/enable")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> enableUser(@RequestBody UserInfoRequest userInfoRequest) {
        User user = userService.findById(userInfoRequest.getUserId());
        user.setEnable(true);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "user query success",userRepository.save(user)));
    }
}

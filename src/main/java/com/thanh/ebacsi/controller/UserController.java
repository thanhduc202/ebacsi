package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.entity.Role;
import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.repository.UserRepository;
import com.thanh.ebacsi.dto.response.TokenResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.service.role.RoleService;
import com.thanh.ebacsi.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/view")
    ResponseEntity<ResponseObject> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "User query success", userService.findAll()));
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
        if (foundUser != null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new UserInfoResponse());
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

    @DeleteMapping("/delete")
    ResponseEntity<ResponseObject> delete(@PathVariable String username) {
        return userService.delete(username);
    }

}
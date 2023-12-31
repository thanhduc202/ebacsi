package com.thanh.ebacsi.controller;

import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.TokenResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

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
        return userService.findByUserName(userInfoRequest);
    }

    @PostMapping("/auth/login")
    ResponseEntity<TokenResponse> authenticate(@RequestBody UserInfoRequest userInfoRequest) {
        return userService.login(userInfoRequest);
    }

    @PostMapping("/auth/register")
    ResponseEntity<UserInfoResponse> insertUser(@RequestBody UserInfoRequest userInfoRequest) {
        return userService.insertUser(userInfoRequest);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR')")
    ResponseEntity<UserInfoResponse> updateUser(@RequestBody UserInfoRequest userInfoRequest) {
        return userService.updateUser(userInfoRequest);
    }

    @PutMapping("/changePass")
    ResponseEntity<UserInfoResponse> changePassword(@RequestBody UserInfoRequest userInfoRequest, @RequestHeader("Authorization") String token) {
        return userService.changePassword(userInfoRequest, token);
    }

    @PutMapping("/disable")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> disableUser(@RequestBody UserInfoRequest userInfoRequest) {
        return userService.disableUser(userInfoRequest);
    }

    @PutMapping("/enable")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<ResponseObject> enableUser(@RequestBody UserInfoRequest userInfoRequest) {
        return userService.enableUser(userInfoRequest);
    }
}

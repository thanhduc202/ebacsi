package com.thanh.ebacsi.service;

import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.entity.User;

import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.TokenResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {
    ResponseEntity<TokenResponse> login(UserInfoRequest userInfoRequest);

    ResponseEntity<ResponseObject> delete(String username);

    ResponseEntity<UserInfoResponse> findByUserName(UserInfoRequest userInfoRequest);

    User findById(Long id);

    List<UserInfoResponse> findAll();

    List<UserInfoResponse> getUserEnable();

    ResponseEntity<UserInfoResponse> changePassword(UserInfoRequest userInfoRequest, String token);

    ResponseEntity<ResponseObject> disableUser(UserInfoRequest userInfoRequest);

    ResponseEntity<ResponseObject> enableUser(UserInfoRequest userInfoRequest);

    ResponseEntity<UserInfoResponse> insertUser(UserInfoRequest userInfoRequest);

    ResponseEntity<UserInfoResponse> updateUser(UserInfoRequest userInfoRequest);

}

package com.thanh.ebacsi.service.user;

import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.entity.User;

import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.TokenResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public ResponseEntity<TokenResponse> login(UserInfoRequest userInfoRequest);

    public ResponseEntity<ResponseObject> delete(String username);

    public User findByUserName(String username);

    public User findById(Long id);

    public List<UserInfoResponse> findAll();
}

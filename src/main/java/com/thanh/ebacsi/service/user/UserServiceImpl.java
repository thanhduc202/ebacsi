package com.thanh.ebacsi.service.user;

import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.UserRepository;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.TokenResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.security.CustomUserDetailsService;
import com.thanh.ebacsi.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<TokenResponse> login(UserInfoRequest userInfoRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userInfoRequest.getUsername(), userInfoRequest.getPassword()));
            final UserDetails userDetail = customUserDetailsService.loadUserByUsername(userInfoRequest.getUsername());
            if (userDetail != null) {
                return ResponseEntity.ok().body(new TokenResponse("Success", "Login success !", jwtUtils.generateToken(userDetail)));

            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new TokenResponse("Failed", "Wrong username or password, try again", ""));
        }
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> delete(String username) {
        User foundUser = userRepository.findByUsername(username);
        if (foundUser == null) {
            throw new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "User query success", userRepository.deleteUsersByUsername(username)));
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new NotFoundException("Not found user");
        }
        return foundUser.get();
    }

    @Override
    public List<UserInfoResponse> findAll() {
        return userRepository.findAllUser();
    }
}

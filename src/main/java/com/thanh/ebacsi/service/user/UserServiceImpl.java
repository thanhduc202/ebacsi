package com.thanh.ebacsi.service.user;

import com.thanh.ebacsi.dto.request.UserInfoRequest;
import com.thanh.ebacsi.entity.Role;
import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.RoleRepository;
import com.thanh.ebacsi.repository.UserRepository;
import com.thanh.ebacsi.dto.response.ResponseObject;
import com.thanh.ebacsi.dto.response.TokenResponse;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import com.thanh.ebacsi.security.Convert;
import com.thanh.ebacsi.security.CustomUserDetailsService;
import com.thanh.ebacsi.security.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private RoleRepository roleRepository;

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
    public ResponseEntity<UserInfoResponse> findByUserName(UserInfoRequest userInfoRequest) {
        User user = userRepository.findByUsername(userInfoRequest.getUsername());
        if(user == null){
            throw new NotFoundException("Not found user!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse(user));
    }

    @Override
    public User findById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new NotFoundException("Not found user");
        }
        return foundUser.get();
    }

    @Override
    public List<UserInfoResponse> findAll() {
        return userRepository.findAllUser();
    }

    @Override
    public List<UserInfoResponse> getUserEnable() {
        return userRepository.getUserEnable();
    }

    @Override
    public ResponseEntity<UserInfoResponse> changePassword(UserInfoRequest userInfoRequest, String token) {
        token = Convert.bearerTokenToToken(token);
        User user = userRepository.findByUsername(jwtUtils.extractUsername(token));
        user.setPassword(userInfoRequest.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse(userRepository.save(user)));
    }

    @Override
    public ResponseEntity<ResponseObject> disableUser(UserInfoRequest userInfoRequest) {
        User user = userRepository.findByUserId(userInfoRequest.getUserId());
        if(!user.getEnable()){
            throw new NotFoundException("User has disable!");
        }
        user.setEnable(false);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "user query success",userRepository.save(user)));
    }

    @Override
    public ResponseEntity<ResponseObject> enableUser(UserInfoRequest userInfoRequest) {
        User user = userRepository.findByUserId(userInfoRequest.getUserId());
        if(user.getEnable()){
            throw new NotFoundException("User has enable!");
        }
        user.setEnable(true);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "user query success",userRepository.save(user)));
    }

    @Override
    public ResponseEntity<UserInfoResponse> insertUser(UserInfoRequest userInfoRequest) {
        User foundUser = userRepository.findByUsername(userInfoRequest.getUsername().trim());
        if (foundUser != null) {
            throw new NotFoundException("Username have existed!");
        }
        //Register user auto save role is USER
        Role foundRole = roleRepository.getRoleDefault();
        if (foundRole == null) {
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

    @Override
    public ResponseEntity<UserInfoResponse> updateUser(UserInfoRequest userInfoRequest) {
        if (userInfoRequest.getUserId() == null) {
            throw new NotFoundException("Not found user");
        }
        User existedUser = userRepository.findByUsername(userInfoRequest.getUsername());
        User user = findById(userInfoRequest.getUserId());
        if(existedUser != null && !existedUser.getUserId().equals(user.getUserId())){
            throw new NotFoundException("username has existed! Please try again");
        }
        user.setUsername(userInfoRequest.getUsername());
        user.setPassword(userInfoRequest.getPassword());
        User result = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new UserInfoResponse(result));
    }
}

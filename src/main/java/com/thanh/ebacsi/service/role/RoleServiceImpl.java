package com.thanh.ebacsi.service.role;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Role findByRoleId(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isEmpty()) {
            throw new RuntimeException("Not found role");
        }
        return role.get();
    }

    @Override
    public List<RoleResponse> getAllRole() {
        List<RoleResponse> list = roleRepository.findAllRole();
        if (list.size() < 0) {
            throw new NotFoundException("Not found Role");
        }
        return list;
    }

    @Override
    public Role getRoleDefault() {
        return roleRepository.getRoleDefault();
    }

    @Override
    public Role getRoleByRoleId(Long roleId) {
        return roleRepository.getRoleByRoleId(roleId);
    }

    @Override
    public List<RoleResponse> getRoleByUserId(Long userId) {
        return roleRepository.getRoleByUserId(userId);
    }

    @Override
    public ResponseEntity<ResponseObject> getRoleByUser(String token) {
        token = Convert.bearerTokenToToken(token);
        User user = userRepository.findByUsername(jwtUtils.extractUsername(token));
        Long userId = user.getUserId();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Role query success", getRoleByUserId(userId)));
    }

    @Override
    public ResponseEntity<RoleResponse> insertRole(RoleRequest roleRequest) {
        Role role = new Role(roleRequest);
        Role roleExisted = roleRepository.findByRoleName(roleRequest.getRoleName());
        if (roleExisted != null && !roleExisted.getRoleId().equals(role.getRoleId())) {
            throw new NotFoundException("RoleName have existed");
        }
        role.setRoleName(roleRequest.getRoleName());
        Role result = roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.OK).body(new RoleResponse(result));
    }

    @Override
    public ResponseEntity<UserInfoResponse> insertRoleToUser(RoleRequest roleRequest) {
        User user = userRepository.findByUserId(roleRequest.getUserId());
        Role role = roleRepository.getRoleByRoleId(roleRequest.getRoleId());
        user.getRole().add(role);
        User result = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse((result)));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteRole(Long roleId) {
        boolean exists = roleRepository.existsById(roleId);
        roleRepository.deleteById(roleId);
        if (exists) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Delete successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Failed", "Not Found role to delete", ""));
    }

    @Override
    public ResponseEntity<UserInfoResponse> deleteRoleOfUser(RoleRequest roleRequest) {
        Role role = roleRepository.getRoleByRoleId(roleRequest.getRoleId());
        User user = userRepository.findByUserId(roleRequest.getUserId());
        if (role != null) {
            user.getRole().remove(role);
            return ResponseEntity.status(HttpStatus.OK).body(new UserInfoResponse(userRepository.save(user)));
        }
        throw new NotFoundException("Not found role of User");
    }

}

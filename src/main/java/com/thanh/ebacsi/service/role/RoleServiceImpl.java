package com.thanh.ebacsi.service.role;

import com.thanh.ebacsi.dto.response.RoleResponse;
import com.thanh.ebacsi.entity.Role;
import com.thanh.ebacsi.exception.NotFoundException;
import com.thanh.ebacsi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role insertRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByRoleId(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (!role.isPresent()) {
            throw new RuntimeException("Not found role");
        }
        return role.get();
    }

    @Override
    public List<RoleResponse> getAllRole() {
        List<RoleResponse> list = roleRepository.findAllRole();
        if(list.size()<0){
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

}

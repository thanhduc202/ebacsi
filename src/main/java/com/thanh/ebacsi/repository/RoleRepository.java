package com.thanh.ebacsi.repository;

import com.thanh.ebacsi.dto.response.RoleResponse;
import com.thanh.ebacsi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r")
    public List<RoleResponse> findAllRole();

    @Query("select r from Role r where r.roleId = 2")
    public Role getRoleDefault();

    @Query("select r from Role r where r.roleId = ?1")
    public Role getRoleByRoleId(Long roleId);
}

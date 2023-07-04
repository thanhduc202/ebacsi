package com.thanh.ebacsi.repository;

import com.thanh.ebacsi.dto.response.RoleResponse;
import com.thanh.ebacsi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("""
            select r from Role r where r.roleName = :roleName
            """)
    Role findByRoleName(String roleName);

    @Query("select r from Role r")
    List<RoleResponse> findAllRole();

    @Query("select r from Role r where r.roleId = 2")
    Role getRoleDefault();

    @Query("select r from Role r where r.roleId = ?1")
    Role getRoleByRoleId(Long roleId);

    @Query("""
            select r from Role r
            inner join r.user u
            WHERE u.userId = :userId
            """)
    List<RoleResponse> getRoleByUserId(Long userId);
}

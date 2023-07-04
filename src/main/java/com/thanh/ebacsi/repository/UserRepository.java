package com.thanh.ebacsi.repository;

import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
            select u from User u where u.username = :username
            """)
    User findByUsername(String username);

    User deleteUsersByUsername(String username);

    @Query("select u from User u")
    List<UserInfoResponse> findAllUser();

    @Query("select u from User u where u.userId=?1")
    User findByUserId(Long userId);

    @Query("""
            select u from User u where u.enable = true
            """)
    List<UserInfoResponse> getUserEnable();
}


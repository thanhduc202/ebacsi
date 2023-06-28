package com.thanh.ebacsi.repository;

import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.dto.response.UserInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);

    public User deleteUsersByUsername(String username);

    @Query("select u from User u")
    public List<UserInfoResponse> findAllUser();

    @Query("select u from User u where u.userId=?1")
    public User findByUserId(Long userId);

    @Query("""
            select u from User u where u.enable = true
            """)
    public List<UserInfoResponse> getUserEnable();
}


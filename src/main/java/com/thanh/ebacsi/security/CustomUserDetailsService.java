package com.thanh.ebacsi.security;

import com.thanh.ebacsi.entity.User;
import com.thanh.ebacsi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User users =  userRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new CustomUserDetails(users);
    }
}

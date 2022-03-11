package com.example.crowdfunding.config;

import com.example.crowdfunding.user.User;
import com.example.crowdfunding.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        Set<GrantedAuthority> grantedAuthorities;
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");

        } else  {
            grantedAuthorities = new HashSet<>();
        }
//        else {
//            throw new UsernameNotFoundException("Unauthorised user");
//        }

        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}

package com.store.services.impl;

import com.store.entities.User;
import com.store.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Username is taken as email
        User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with this email"));
        return user;
    }
}

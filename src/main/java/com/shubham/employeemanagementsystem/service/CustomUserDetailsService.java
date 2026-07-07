package com.shubham.employeemanagementsystem.service;

import com.shubham.employeemanagementsystem.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.shubham.employeemanagementsystem.entity.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        System.out.println("Loading user: " + username);
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("Authorities from DB: " +
                appUser.getRoles().stream()
                        .map(role -> role.getRoleName())
                        .toList());
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(
                        appUser.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                                .toList()
                )
                .build();
    }

}
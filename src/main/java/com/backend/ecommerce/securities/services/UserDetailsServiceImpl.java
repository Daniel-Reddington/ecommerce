package com.backend.ecommerce.securities.services;

import com.backend.ecommerce.entities.AppUser;
import com.backend.ecommerce.services.interfaces.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = appUserService.loadUserByUserName(username);

        if(appUser == null) throw new BadCredentialsException("Bad credentials");

        Collection<GrantedAuthority> authorities = appUser.getAppRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);

    }

}

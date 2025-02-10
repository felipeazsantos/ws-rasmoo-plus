package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.mysql.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userDetailsRepository.findByUsername(username)
               .orElseThrow(() -> new NotFoundException("Usuario não encontrado: " + username));
    }
}

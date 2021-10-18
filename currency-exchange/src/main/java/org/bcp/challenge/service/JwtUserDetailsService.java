package org.bcp.challenge.service;

import org.bcp.challenge.exceptions.JwtNotFoundException;
import org.bcp.challenge.dto.UserPrincipal;
import org.bcp.challenge.repository.UserRepository;
import org.bcp.challenge.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user))
            throw new JwtNotFoundException("Credentials not allowed.");
        return new UserPrincipal(user);
    }
}

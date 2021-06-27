package org.jalvarova.currency.service;

import org.jalvarova.currency.dto.UserPrincipal;
import org.jalvarova.currency.exceptions.JwtNotFoundException;
import org.jalvarova.currency.repository.UserRepository;
import org.jalvarova.currency.repository.entity.User;
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

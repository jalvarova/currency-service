package org.bcp.challenge.currencyexchange.service;

import org.bcp.challenge.currencyexchange.dto.UserPrincipal;
import org.bcp.challenge.currencyexchange.exceptions.JwtNotFoundException;
import org.bcp.challenge.currencyexchange.repository.UserRepository;
import org.bcp.challenge.currencyexchange.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new JwtNotFoundException("Credentials not allowed.");
        return new UserPrincipal(user);
    }
}

package com.twitterClone.twitterClone.service;

import com.twitterClone.twitterClone.entity.Role;
import com.twitterClone.twitterClone.entity.User;
import com.twitterClone.twitterClone.exceptions.UserAlreadyRegisterException;
import com.twitterClone.twitterClone.repository.RoleRepository;
import com.twitterClone.twitterClone.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Override
    public User register(String username,String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);


        if (optionalUser.isPresent()) {
            throw new UserAlreadyRegisterException("Email Already Exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        Optional<Role> userRole = roleRepository.findByAuthority("USER");
        Role role = userRole.orElseGet(() -> {
            Role newRole = new Role();
            newRole.setAuthority("USER");
            return roleRepository.save(newRole);
        });


        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRoles(new HashSet<>(Set.of(userRole.get())));
        return userRepository.save(user);

    }

    @Transactional
    @Override
    public User login(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        if (authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            return user;
        } else {
            throw new RuntimeException("Invalid email or password");
        }

    }
}

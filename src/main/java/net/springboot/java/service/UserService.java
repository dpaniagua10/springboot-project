package net.springboot.java.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.springboot.java.model.User;
import net.springboot.java.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}

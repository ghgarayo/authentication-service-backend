package br.com.ghx.authenticationservice.services.impl;

import br.com.ghx.authenticationservice.domain.User;
import br.com.ghx.authenticationservice.repository.UserRepository;
import br.com.ghx.authenticationservice.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User register(String name, String username, String password, boolean isAdmin) {
        var hashedPassword = encoder.encode(password);
        var user = new User(name, username, hashedPassword, isAdmin);

        return userRepository.save(user);
    }
}

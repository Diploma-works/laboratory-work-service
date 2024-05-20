package com.vko.labworkproducer.service;

import com.vko.labworkproducer.entity.User;
import com.vko.labworkproducer.exception.UsernameAlreadyExistsException;
import com.vko.labworkproducer.exception.UsernameOrPasswordException;
import com.vko.labworkproducer.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        if (Optional.ofNullable(findByUsername(user.getUsername())).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = Optional.ofNullable(findByUsername(username));
        if (user.isEmpty()) {
            throw new UsernameOrPasswordException();
        }
        return user.get();
    }
}
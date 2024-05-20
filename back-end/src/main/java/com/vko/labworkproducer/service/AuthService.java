package com.vko.labworkproducer.service;

import com.vko.labworkproducer.dto.AuthDto;
import com.vko.labworkproducer.dto.LoginResponseDto;
import com.vko.labworkproducer.dto.VerifyTokenResponseDto;
import com.vko.labworkproducer.entity.User;
import com.vko.labworkproducer.exception.UsernameOrPasswordException;
import com.vko.labworkproducer.utils.JwtTokensUtil;
import com.vko.labworkproducer.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokensUtil jwtTokensUtil;

    public User register(AuthDto dto) {
        User user = userMapper.authDtoToUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.createUser(user);
    }

    public LoginResponseDto login(AuthDto dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.username(),
                            dto.password()
                    ));
        } catch (BadCredentialsException e) {
            throw new UsernameOrPasswordException();
        }
        User user = userService.findByUsername(dto.username());
        return new LoginResponseDto(jwtTokensUtil.generateToken(user));
    }

    public VerifyTokenResponseDto verifyToken(String token) {
        return jwtTokensUtil.verifyToken(token);
    }

}

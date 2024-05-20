package com.vko.labworkproducer.controller;

import com.vko.labworkproducer.dto.AuthDto;
import com.vko.labworkproducer.dto.TokenDto;
import com.vko.labworkproducer.dto.VerifyTokenResponseDto;
import com.vko.labworkproducer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDto request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestBody TokenDto jwtToken) {
        VerifyTokenResponseDto verifyTokenResponseDto = authService.verifyToken(jwtToken.jwtToken());
        return new ResponseEntity<>(verifyTokenResponseDto.response(), verifyTokenResponseDto.status());
    }

}

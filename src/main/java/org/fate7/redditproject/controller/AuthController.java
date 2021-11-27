package org.fate7.redditproject.controller;

import lombok.RequiredArgsConstructor;
import org.fate7.redditproject.dto.AuthenticationResponse;
import org.fate7.redditproject.dto.LoginRequest;
import org.fate7.redditproject.dto.RegisterRequest;
import org.fate7.redditproject.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
        authService.signUp(registerRequest);
        return ResponseEntity.ok().body("User registration successful !");
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {
        authService.verifyToken(token);
        return ResponseEntity.ok().body("Verification successful !");

    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}

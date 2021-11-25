package org.fate7.redditproject.controller;

import lombok.RequiredArgsConstructor;
import org.fate7.redditproject.dto.RegisterRequest;
import org.fate7.redditproject.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signUp(@RequestBody RegisterRequest registerRequest){
        authService.signUp(registerRequest);
    }
}

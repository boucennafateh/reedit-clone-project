package org.fate7.redditproject.service;

import lombok.RequiredArgsConstructor;
import org.fate7.redditproject.dto.RegisterRequest;
import org.fate7.redditproject.model.User;
import org.fate7.redditproject.model.VerificationToken;
import org.fate7.redditproject.repository.UserRepository;
import org.fate7.redditproject.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signUp(RegisterRequest registerRequest){

        User user = User.builder()
                .username(registerRequest.getUserName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .created(Instant.now())
                .enabled(false)
                .build();
        userRepository.save(user);
        String token = createVerificationToken(user);


    }

    private String createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .user(user)
                .token(token)
                .build();
        verificationTokenRepository.save(verificationToken);
        return token;

    }
}
